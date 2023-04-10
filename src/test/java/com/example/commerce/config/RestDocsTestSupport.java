package com.example.commerce.config;

import com.example.commerce.business.auth.util.TokenProvider;
import com.example.commerce.business.cart.domain.Cart;
import com.example.commerce.business.cart.domain.CartItem;
import com.example.commerce.business.cart.repository.CartItemRepository;
import com.example.commerce.business.cart.repository.CartRepository;
import com.example.commerce.business.category.domain.Category;
import com.example.commerce.business.category.repository.CategoryRepository;
import com.example.commerce.business.coupon.domain.Coupon;
import com.example.commerce.business.coupon.repository.CouponRepository;
import com.example.commerce.business.item.domain.Item;
import com.example.commerce.business.item.domain.ItemImage;
import com.example.commerce.business.item.domain.ItemOption;
import com.example.commerce.business.item.domain.ItemStatus;
import com.example.commerce.business.item.repository.ItemImageRepository;
import com.example.commerce.business.item.repository.ItemOptionRepository;
import com.example.commerce.business.item.repository.ItemRepository;
import com.example.commerce.business.order.domain.*;
import com.example.commerce.business.order.repository.OrderOptionRepository;
import com.example.commerce.business.order.repository.OrderRepository;
import com.example.commerce.business.point.domain.Point;
import com.example.commerce.business.point.domain.PointOption;
import com.example.commerce.business.point.domain.PointType;
import com.example.commerce.business.point.domain.PointUse;
import com.example.commerce.business.point.repository.PointOptionRepository;
import com.example.commerce.business.point.repository.PointRepository;
import com.example.commerce.business.promotion.domain.Promotion;
import com.example.commerce.business.promotion.domain.PromotionLog;
import com.example.commerce.business.promotion.repository.PromotionLogRepository;
import com.example.commerce.business.promotion.repository.PromotionRepository;
import com.example.commerce.business.review.domain.Review;
import com.example.commerce.business.review.repository.ReviewRepository;
import com.example.commerce.business.user.domain.Address;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.business.user.repository.AddressRepository;
import com.example.commerce.business.user.repository.UserRepository;
import com.example.commerce.common.constant.JoinType;
import com.example.commerce.common.constant.Role;
import com.example.commerce.user.UserControllerTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import io.micrometer.core.instrument.util.IOUtils;

import javax.transaction.Transactional;

@Transactional
@SpringBootTest(properties = {"spring.config.location=classpath:application-test.yml"})
@AutoConfigureMockMvc
@Import(RestDocsConfig.class)
@ExtendWith(RestDocumentationExtension.class)
public class RestDocsTestSupport {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected RestDocumentationResultHandler restDocs;
    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ItemOptionRepository optionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemImageRepository imageRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderOptionRepository orderOptionRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private PointOptionRepository pointOptionRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private PromotionLogRepository promotionLogRepository;

    @BeforeEach
    void setUp(final RestDocumentationContextProvider provider,
               final WebApplicationContext context)
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider)) // rest docs 설정 주입
                .alwaysDo(MockMvcResultHandlers.print()) // andDo(print()) 코드 포함
                .alwaysDo(restDocs) // pretty 패턴과 문서 디렉토리 명 정해준 것 적용
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
                .build();
    }

    public User adminSave() {
        final String email = "admin@example.com";
        final String password = "12341234";
        User user = User.builder()
                .name("admin")
                .email(email)
                .phone("01012345678")
                .password(password)
                .joinType(JoinType.COMMERCE)
                .role(Role.ADMIN)
                .build();

        return userRepository.save(user);
    }

    public User userSave() {
        final String email = "test@example.com";
        final String password = "12341234";
        User user = User.builder()
                .name("tester")
                .email(email)
                .phone("01012345678")
                .password(password)
                .joinType(JoinType.COMMERCE)
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }
    public String getTokenByUser(User user) {
        return tokenProvider.generateToken(user.getId());
    }

    public String getToken() {
        final String email = "test@example.com";
        final String password = "12341234";
        User user = User.builder()
                .name("tester")
                .email(email)
                .phone("01012345678")
                .password(password)
                .joinType(JoinType.COMMERCE)
                .role(Role.USER)
                .build();
        final User save = userRepository.save(user);
        saveCart(save);
        return tokenProvider.generateToken(save.getId());
    }
    public void saveCartItem(Item item, Cart cart) {
        final CartItem cartItem = CartItem.builder()
                .cart(cart)
                .item(item)
                .itemOption(item.getOptions().get(0))
                .build();
        cartItemRepository.save(cartItem);
    }

    public String getAdminToken() {
        final String email = "admin@admin.com";
        final String password = "12341123";
        User user = User.builder()
                .name("admin")
                .email(email)
                .phone("01012345678")
                .password(password)
                .joinType(JoinType.COMMERCE)
                .role(Role.ADMIN)
                .build();
        return tokenProvider.generateToken(userRepository.save(user).getId());
    }

    public void saveAddress(String token) {
        final Long userId = tokenProvider.getUserId(token);
        final Optional<User> user = userRepository.findById(userId);
        Address address1 = Address.builder()
                .user(user.get())
                .zipcode("01234")
                .road("서울특별시 동작구 상도1동 809")
                .jiBun("서울특별시 동작구 상도1동 809")
                .building("빌딩")
                .detail("1 - 132")
                .build();
        Address address2 = Address.builder()
                .user(user.get())
                .zipcode("011222")
                .road("서울특별시 동작구 상도1동 809")
                .jiBun("서울특별시 동작구 상도1동 809")
                .building("빌딩")
                .detail("1 - 132")
                .build();
        Address address3 = Address.builder()
                .user(user.get())
                .zipcode("42351")
                .road("서울특별시 동작구 상도1동 809")
                .jiBun("서울특별시 동작구 상도1동 809")
                .building("빌딩")
                .detail("1 - 132")
                .build();
        List<Address> addressList = new ArrayList<>(Arrays.asList(address1, address2, address3));
        addressRepository.saveAll(addressList);
    }

    public List<Item> addItems() {
        List<Item> items = new ArrayList<>();
        final Category category = addCategory();
        for (int i = 1; i < 20; i++) {
            final ItemImage itemImage = addItemImage();
            final ItemOption itemOption = addItemOption();
            final Item item = Item.builder()
                    .name("흰색 와이셔츠")
                    .itemStatus(ItemStatus.SELL)
                    .itemImages(addItemImages())
                    .options(new ArrayList<>(Arrays.asList(itemOption)))
                    .build();
            items.add(itemRepository.save(item));
        }
        return items;
    }
    public List<ItemImage> addItemImages() {
        List<ItemImage> itemImages = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            final ItemImage itemImage = ItemImage.builder()
                    .imageUrl("https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E")
                    .viewYn(1)
                    .orderedSeq(1)
                    .build();
            itemImages.add(imageRepository.save(itemImage));
        }
        return itemImages;
    }


    public ItemImage addItemImage() {
        final ItemImage itemImage = ItemImage.builder()
                .imageUrl("https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E")
                .viewYn(1)
                .orderedSeq(1)
                .build();
        return imageRepository.save(itemImage);
    }

    public Category addCategory() {
        final Category category = Category.builder()
                .classificationOne("패션의류")
                .classificationTwo("명품/수입의류")
                .classificationThree("남성 팬츠")
                .classificationDetail("데님 팬츠")
                .build();
        return categoryRepository.save(category);
    }

    public ItemOption addItemOption() {
        ItemOption itemOption = ItemOption.builder()
                .optionPrice(100000)
                .optionStock(10)
                .optionSize("L")
                .optionColor("white")
                .optionWeight(2.5)
                .build();

        return optionRepository.save(itemOption);
    }

    public Cart saveCart(User user) {
        final Cart cart = Cart.builder()
                .user(user)
                .build();
        return cartRepository.save(cart);
    }
    public ItemOption findById(Long optionId) {
        return optionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalThreadStateException("여기"));
    }

    public Item addItem() {
        final Category category = addCategory();
        final ItemImage itemImage = addItemImage();
        final ItemOption option = addItemOption();

        final Item item = Item.builder()
                .name("흰색 와이셔츠")
//                .price(34900)
//                .stock(10)
//                .category(category)
                .itemStatus(ItemStatus.SELL)
                .itemImages(new ArrayList<>(Arrays.asList(itemImage)))
                .options(new ArrayList<>(Arrays.asList(option)))
                .build();
        return itemRepository.save(item);
    }

    public Orders addOrder() {
        final User user = userSave();
        final Item item = addItem();

        final Orders orders = Orders.builder()
                .deliveryFee(2500)
//                .orderStatus(OrderStatus.ORDER)
                .paymentStatus(PaymentStatus.CARD)
                .user(user)
//                .item(Collections.singletonList(item))
//                .itemOption(Collections.singletonList(item.getOptions().get(0)))
                .build();
        final OrderOption option = OrderOption.builder()
                .item(item)
                .itemOption(item.getOptions().get(0))
                .price(25000)
                .stock(2)
                .deliveryStatus(DeliveryStatus.PREPARATION)
                .orderStatus(OrderStatus.ORDER)
                .build();
        orderOptionRepository.save(option);
        return orderRepository.save(orders);
    }

    public Orders addOrder(User user) {
        final Item item = addItem();

        final Orders orders = Orders.builder()
                .deliveryFee(2500)
//                .orderStatus(OrderStatus.ORDER)
                .paymentStatus(PaymentStatus.CARD)
                .user(user)
                .build();
        final Orders saveOrder = orderRepository.save(orders);
        final OrderOption option = OrderOption.builder()
                .orders(saveOrder)
                .item(item)
                .itemOption(item.getOptions().get(0))
                .price(25000)
                .stock(2)
                .orderStatus(OrderStatus.ORDER)
                .deliveryStatus(DeliveryStatus.PREPARATION)
                .build();
        orderOptionRepository.save(option);
        return saveOrder;
    }

    public Orders addOrders(User user) {
        final List<Item> items = addItems();

        final Orders orders = Orders.builder()
                .deliveryFee(2500)
//                .orderStatus(OrderStatus.ORDER)
                .paymentStatus(PaymentStatus.CARD)
                .user(user)
                .build();
        final Orders saveOrder = orderRepository.save(orders);
        for (int i = 1; i < 5; i++) {
            final OrderOption option = OrderOption.builder()
                    .orders(saveOrder)
                    .item(items.get(i))
                    .itemOption(items.get(i).getOptions().get(0))
                    .price(25000 * i)
                    .stock(2 * i)
                    .orderStatus(OrderStatus.ORDER)
                    .deliveryStatus(DeliveryStatus.PREPARATION)
                    .build();
            orderOptionRepository.save(option);
        }
        return saveOrder;
    }
    public OrderOption addOrderOption(User user) {
        final Item item = addItem();

        final Orders orders = Orders.builder()
                .deliveryFee(2500)
//                .orderStatus(OrderStatus.ORDER)
                .paymentStatus(PaymentStatus.CARD)
                .user(user)
                .build();
        final Orders saveOrder = orderRepository.save(orders);
        final OrderOption option = OrderOption.builder()
                .orders(saveOrder)
                .item(item)
                .itemOption(item.getOptions().get(0))
                .price(25000)
                .stock(2)
                .orderStatus(OrderStatus.ORDER)
                .deliveryStatus(DeliveryStatus.PREPARATION)
                .build();

        return orderOptionRepository.save(option);
    }
    public Review addReview(User user) {
        final Item item = addItem();

        final Orders orders = Orders.builder()
                .deliveryFee(2500)
//                .orderStatus(OrderStatus.ORDER)
                .paymentStatus(PaymentStatus.CARD)
                .user(user)
                .build();
        final Orders saveOrder = orderRepository.save(orders);
        final OrderOption option = OrderOption.builder()
                .orders(saveOrder)
                .item(item)
                .itemOption(item.getOptions().get(0))
                .price(25000)
                .stock(2)
                .orderStatus(OrderStatus.ORDER)
                .deliveryStatus(DeliveryStatus.PREPARATION)
                .build();
        final Review review = Review.builder()
                .user(user)
                .item(item)
                .orderOption(option)
                .content("가격에 비해 상당히 괜찮네요.")
                .star(4.3)
                .build();
        return reviewRepository.save(review);
    }

    public void addPoints(User user) {
        final Point point = Point.builder()
                .user(user)
                .point(1000)
                .deleteYn(0)
                .build();
        final Point save = pointRepository.save(point);
        String [] type = {"TEXT_REVIEW", "PHOTO_REVIEW", "ORDER", "EVENT"};
        for (int i = 1; i < 11; i++) {
            final PointOption pointOption = PointOption.builder()
                    .point(save)
                    .money(50)
                    .pointUse(i % 2 == 0 ? PointUse.USE : PointUse.SAVE)
                    .pointType(PointType.valueOf(type[i % 4]))
                    .build();
            pointOptionRepository.save(pointOption);
        }
    }

    public List<Coupon> addCoupons(User user) {
        List<Coupon> coupons = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            final Coupon coupon = Coupon.builder()
                    .user(user)
                    .couponUse(i % 2 == 0 ? true : false)
                    .condition(1000 * i)
                    .discount(500 * i)
                    .expiredDate(LocalDateTime.now().plusMonths(6).withNano(0))
                    .build();
            coupons.add(couponRepository.save(coupon));
        }
        return coupons;
    }
    public List<Promotion> addPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        final List<Item> items = addItems();

        for (int i = 1; i <= items.size(); i++) {
            List<ItemOption> options = items.get(i - 1).getOptions();
            Promotion promotion = Promotion.builder()
                    .id((long) i)
                    .itemOption(options.get(0))
                    .discountPercent((5 % i) * i)
                    .salePrice(20000 * i)
                    .stock(20)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusWeeks(i))
                    .useCoupon(i % 2 == 0 ? false : true)
                    .usePoint(i % 2 == 0 ? true : false)
                    .build();
            promotions.add(promotionRepository.save(promotion));
        }
        return promotions;
    }
    public List<PromotionLog> addPromotionLogs(User user) {
        final List<Promotion> promotions = addPromotions();
        List<PromotionLog> promotionLogs = new ArrayList<>();

        for (int i = 0; i < promotions.size(); i++) {
            PromotionLog promotionLog = PromotionLog.builder()
                    .user(user)
                    .promotion(promotions.get(i))
                    .stock(i + 2 * 2)
                    .orderPrice(20000 + i * 2)
                    .usePoint(100)
                    .build();
            promotionLogs.add(promotionLogRepository.save(promotionLog));
        }
        return promotionLogs;
    }

    protected String readJson(final String path) throws IOException {
        return IOUtils.toString(resourceLoader.getResource("classpath:" + path).getInputStream(),
                StandardCharsets.UTF_8);
    }
    protected String readImage(final String path) throws IOException {
        return IOUtils.toString(resourceLoader.getResource("classpath:" + path).getInputStream());
    }
    protected String createJson(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }
}
