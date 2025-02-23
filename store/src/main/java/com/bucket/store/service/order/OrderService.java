package com.bucket.store.service.order;

import com.bucket.store.dto.order.CreateOrderDto;
import com.bucket.store.dto.order.OrderProductDto;
import com.bucket.store.exception.CustomException;
import com.bucket.store.exception.error.ErrorCode;
import com.bucket.store.model.order.Order;
import com.bucket.store.model.order.OrderProduct;
import com.bucket.store.model.product.Brand;
import com.bucket.store.model.product.Product;
import com.bucket.store.model.user.User;
import com.bucket.store.repository.order.OrderProductRepository;
import com.bucket.store.repository.order.OrderRepository;
import com.bucket.store.repository.product.CustomProductRepository;
import com.bucket.store.repository.product.ProductRepository;
import com.bucket.store.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final CustomProductRepository customProductRepository;

    // 주문 생성
    @Transactional
    public void createOrder(CreateOrderDto.Request request) {
        List<OrderProductDto> orderProductDtoList = request.getOrderProductDtoList();
        User user = userRepository.findByUserId(request.getUserId()).get();
        if (user == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        List<OrderProduct> orderProducts = new ArrayList<>();
        int orderamount = 0;
        Set<Brand> brands = new HashSet<>();
        for (OrderProductDto dto : orderProductDtoList) {
            Product product = customProductRepository.findByProductId(dto.getProductId());
            OrderProduct orderProduct = OrderProduct.builder()
                    .orderQuantity(dto.getAmount())
                    .product(product).build();
            orderProducts.add(orderProduct);

            // 재고 부족시 에러
            if (product.getProductStock() < dto.getAmount()) {
                throw new CustomException(ErrorCode.OUT_OF_STOCK);
            }

            //재고 계산
            product.setProductStock(product.getProductStock() - dto.getAmount());
            productRepository.save(product);

            // 주문 금액 계산
            orderamount += product.getAmount() * dto.getAmount();
            brands.add(product.getBrand());
        }

        // 브랜드별 배송비 합 계산
        int shippingCharge = 0;
        for (Brand brand : brands) {
            shippingCharge += brand.getShippingCharge();
        }

        LocalDate orderDate = request.getOrderDate();
        if (orderDate == null) orderDate = LocalDate.now();

        Order order = Order.builder()
                .orderName(request.getOrderName())
                .deliveryAddress(request.getDeliveryAddress())
                .shippingCharge(shippingCharge)
                .orderStatus(request.getOrderStatus())
                .orderAmount(orderamount)
                .orderDate(orderDate)
                .discountAmount(request.getDiscountAmount())
                .user(user).build();
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDate.now());
        }
        orderRepository.save(order);

        // Order 테이블 연결
        orderProducts.stream()
                .forEach(orderProduct -> orderProduct.setOrder(order));

        orderProductRepository.saveAll(orderProducts);
    }

    // 주문 데이터 1000개 생성
    public void createOrders(int N) {
        Random random = new Random();
        LocalDate startDate = LocalDate.of(2024, 1, 1); // 2024년 1월 1일
        LocalDate endDate = LocalDate.now(); // 현재 날짜
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1; // 총 일자 수

        // 각 일자에 최소 1건의 주문 할당
        Map<LocalDate, Integer> orderCountPerDay = new TreeMap<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            orderCountPerDay.put(date, 1); // 매일 최소 1건 할당
        }

        // 남은 주문 수 계산
        int remainingOrders = N - (int) daysBetween;

        // 남은 주문을 랜덤하게 일자에 분배
        List<LocalDate> dateList = new ArrayList<>(orderCountPerDay.keySet());
        for (int i = 0; i < remainingOrders; i++) {
            LocalDate randomDate = dateList.get(random.nextInt(dateList.size()));
            orderCountPerDay.put(randomDate, orderCountPerDay.get(randomDate) + 1);
        }

        // 날짜만큼 반복
        for (Map.Entry<LocalDate, Integer> entry : orderCountPerDay.entrySet()) {
            // 주문 날짜
            LocalDate date = entry.getKey();
            // 날짜별 주문수
            int orderCount = entry.getValue();
            // 날짜별 주문수만큼 반복
            for (int i = 1; i <= orderCount; i++) {
                List<OrderProductDto> orderProductDtoList = new ArrayList<>();
                // 1~3가지 상품 주문
                int M = 1 + random.nextInt(3);
                // M(1~3)가지 상품 주문
                for (int j = 0; j < M; j++) {
                    OrderProductDto orderproductDto = OrderProductDto.builder()
                            .productId((long) 1 + random.nextInt(100))
                            .amount(1 + random.nextInt(M)).build();
                    orderProductDtoList.add(orderproductDto);
                }

                // createOrder 메서드 입력 데이터
                CreateOrderDto.Request request = CreateOrderDto.Request.builder()
                        .orderStatus("주문완료")
                        .deliveryAddress("서울 동작구 여의대방로20길 33")
                        .discountAmount(1000 + random.nextInt(1000))
                        .orderProductDtoList(orderProductDtoList)
                        // 랜덤한 유저의 주문
                        .userId("user_" + random.nextInt(100))
                        // 주문날짜
                        .orderDate(date)
                        .orderName("user_name")
                        .build();

                createOrder(request);
            }
        }
    }
}
