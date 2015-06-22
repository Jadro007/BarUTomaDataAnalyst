package cz.muni.fi.PB138.main.communication;

import cz.muni.fi.PB138.main.db.*;
import cz.muni.fi.PB138.main.entities.Bar;
import cz.muni.fi.PB138.main.entities.Order;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Martina on 21.6.2015.
 */
public class LoginProcedure {
    public static final String GET_MY_BARS_REQUEST = "/bar/getMyBars";
    public static final String BAR_ORDER_PREFIX = "/bar/";
    public static final String BAR_ORDER_POSTFIX = "/order";
    public static final String USER_ORDER_POSTFIX = "/order/?username=";
    private String token;
    private HTTPRequest httpRequest;
    private boolean isAdmin;
    private String userName;
    private UserInformation ui;
    private long userId;

    public LoginProcedure(String userName, String token) {
        this.userName = userName;
        this.token = token;
        ui = new UserInformationImpl();
        httpRequest = new HTTPRequest();
        isAdmin = httpRequest.isUserAdmin(token);
        userId = ui.getUserId(userName);
    }

    public void UpdateDatabase() {

        ui.saveCurrentUserInformation(userId, isAdmin);

        if (isAdmin) UpdateAdmin();

        UpdateUser();

        ui.saveCurrentUserLastTimeOfUpdate();
    }

    private void UpdateAdmin() {
        LocalDate lastTimeOfUpdate = ui.getCurrentUserLastTimeOfUpdate();
        CreateDocument createDocument = new CreateDocument();
        StoreDatabase storeDatabase = new StoreDatabase();
        ParserBarOrder parserBarOrder = new ParserBarOrder();
        List<Order> orderListAllBars = new ArrayList<>();
        List<Bar> barList;
        String jsonBarOrder;
        String jsonGetMyBars;

        jsonGetMyBars = httpRequest.getRequestWithToken(token, GET_MY_BARS_REQUEST);
        barList = new ParserGetMyBars().parse(jsonGetMyBars);
        for (Bar bar : barList) {
            long barId = bar.getId();
            jsonBarOrder = httpRequest.getRequestWithToken(token, BAR_ORDER_PREFIX + barId + BAR_ORDER_POSTFIX);
            List<Order> barOrders = parserBarOrder.parse(jsonBarOrder, barId);
            orderListAllBars.addAll(barOrders);
        }

        //select only in correct date range
        orderListAllBars.removeIf(o -> !o.getDatetime().isAfter(lastTimeOfUpdate));
        if (!orderListAllBars.isEmpty()) {
            InputStream adminStream = createDocument.createAdminDocument(orderListAllBars);
            storeDatabase.store("admin", adminStream);
        }
        InputStream barStream = createDocument.createBarDocument(barList);
        storeDatabase.store("bar", barStream);
    }

    private void UpdateUser() {
        LocalDate lastTimeOfUpdate = ui.getCurrentUserLastTimeOfUpdate();
        CreateDocument createDocument = new CreateDocument();
        StoreDatabase storeDatabase = new StoreDatabase();
        ParserUserOrders parserUserOrders = new ParserUserOrders();
        List<Long> barIds;
        List<Order> orderListAll = new ArrayList<>();
        String jsonUserOrder;
        String jsonGetBar;

        jsonGetBar = httpRequest.getRequest("/bar");
        barIds = new ParserBar().parse(jsonGetBar);

        for (Long id : barIds) {
            List<Order> orderList;
            jsonUserOrder = httpRequest.getRequestWithToken(token, BAR_ORDER_PREFIX + id + USER_ORDER_POSTFIX + userName);
            orderList = parserUserOrders.parse(jsonUserOrder, id);
            orderListAll.addAll(orderList);
        }

        orderListAll.removeIf(o -> !o.getDatetime().isAfter(lastTimeOfUpdate));

        if (!orderListAll.isEmpty()) {
            InputStream userStream = createDocument.createUserDocument(orderListAll);
            storeDatabase.store("user", userStream);
        }

    }


}
