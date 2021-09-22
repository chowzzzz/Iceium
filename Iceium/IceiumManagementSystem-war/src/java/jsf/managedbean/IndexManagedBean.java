/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import ejb.session.stateless.OrderEntitySessionBeanLocal;
import entity.OrderEntity;
import entity.OrderItemEntity;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScaleLabel;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.optionconfig.tooltip.Tooltip;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.pie.PieChartOptions;
import util.enumeration.AccessRightEnum;
import util.enumeration.OrderItemStatusEnum;

/**
 *
 * @author Theodoric
 */
@Named(value = "indexManagedBean")
@ViewScoped
public class IndexManagedBean implements Serializable
{

    @EJB(name = "CustomerEntitySessionBeanLocal")
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    @EJB(name = "OrderEntitySessionBeanLocal")
    private OrderEntitySessionBeanLocal orderEntitySessionBeanLocal;

    @Resource(name = "IceiumDataSource")
    private DataSource iceiumDataSource;

    private final LocalDate today = LocalDate.now();
    private final SelectItem[] enabledStatuses = new SelectItem[]
    {
        new SelectItem("All"), new SelectItem("True"), new SelectItem("False")
    };

    private List<LocalDate> salesReportDates;

    private BigDecimal lowerBound;
    private BigDecimal upperBound;

    private String enabledStatus;

    private String accessRight;

    private String salesLineModelHex1;
    private String ordersStackedGroupModelHex1;
    private String ordersStackedGroupModelHex2;
    private String orderFulfilmentPieModelHex1;
    private String orderFulfilmentPieModelHex2;
    private String orderFulfilmentPieModelHex3;
    private String orderFulfilmentPieModelHex4;
    private String customerAgesDonutModelHex1;
    private String customerAgesDonutModelHex2;
    private String customerAgesDonutModelHex3;
    private String customerAgesDonutModelHex4;
    private String customerAgesDonutModelHex5;
    private String customerAgesDonutModelHex6;
    private String customerAgesDonutModelHex7;
    private String customerAgesDonutModelHex8;

    private LineChartModel salesLineModel;
    private BarChartModel ordersStackedGroupModel;
    private PieChartModel orderFulfilmentPieModel;
    private DonutChartModel customerAgesDonutModel;

    private List<List<OrderEntity>> orderEntitiesList;
    private HashMap<Integer, Integer> customerAges;

    public IndexManagedBean()
    {
        salesReportDates = new ArrayList<>();

        initializeReport();

        salesLineModelHex1 = "#2AECFF";
        ordersStackedGroupModelHex1 = "#94D690";
        ordersStackedGroupModelHex2 = "#D97B3B";
        orderFulfilmentPieModelHex1 = "#F07F65";
        orderFulfilmentPieModelHex2 = "#784DF0";
        orderFulfilmentPieModelHex3 = "#F0E035";
        orderFulfilmentPieModelHex4 = "#41F0B3";
        customerAgesDonutModelHex1 = "#FFFCCA";
        customerAgesDonutModelHex2 = "#C5E663";
        customerAgesDonutModelHex3 = "#A5FC79";
        customerAgesDonutModelHex4 = "#A667FF";
        customerAgesDonutModelHex5 = "#63D7E6";
        customerAgesDonutModelHex6 = "#A5FC79";
        customerAgesDonutModelHex7 = "#E6BE63";
        customerAgesDonutModelHex8 = "#FE726D";
    }

    @PostConstruct
    public void postConstruct()
    {
        orderEntitiesList = orderEntitySessionBeanLocal.retrieveAllOrdersFromPastTwelveMonths();
        customerAges = customerEntitySessionBeanLocal.retrieveAllCustomerAges();

        createSalesLineChartModel();
        createOrdersStackedGroupModel();
        createOrderFulfilmentPieModel();
        createCustomerAgesDonutModel();
    }

    private void initializeReport()
    {
        LocalDate todayMinusOneYear = today.minusYears(1);
        salesReportDates.add(todayMinusOneYear);
        salesReportDates.add(today);

        lowerBound = BigDecimal.ZERO;
        upperBound = BigDecimal.valueOf(100000);

        enabledStatus = "All";

        accessRight = "All";
    }

    public void generateSalesReport()
    {
        try
        {
            FacesContext.getCurrentInstance().getExternalContext().responseReset();

            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("imagePath", "http://localhost:8080/IceiumManagementSystem-war/jasperreport/logo_cut_smaller.png");

            LocalDate startDateTime = salesReportDates.get(0);
            LocalDate endDateTime = salesReportDates.get(1);

            Timestamp startTimestamp = Timestamp.valueOf(LocalDateTime.of(startDateTime, LocalTime.MIN));
            Timestamp endTimestamp = Timestamp.valueOf(LocalDateTime.of(endDateTime, LocalTime.MIN));

            parameters.put("startTimestamp", startTimestamp);
            parameters.put("endTimestamp", endTimestamp);

            generateReport("Iceium_Sales_Report", parameters);

            initializeReport();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sales report generated for " + startDateTime + "-" + endDateTime, null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured while generating sales report", null));
        }
    }

    public void generateCustomerReport()
    {
        try
        {
            FacesContext.getCurrentInstance().getExternalContext().responseReset();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("lowerBound", lowerBound);
            parameters.put("upperBound", upperBound);
            parameters.put("enabledStatus", enabledStatus);

            generateReport("Iceium_Customer_Report", parameters);

            initializeReport();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer report generated for " + lowerBound + "-" + upperBound, null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured while generating customer report", null));
        }
    }

    public void generateStaffReport()
    {
        try
        {
            FacesContext.getCurrentInstance().getExternalContext().responseReset();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("accessRight", accessRight);

            generateReport("Iceium_Staff_Report", parameters);

            initializeReport();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Staff report generated for " + accessRight, null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured while generating staff report", null));
        }
    }
    
    private void generateReport(String fileName, Map<String, Object> parameters) throws Exception
    {
        InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreport/" + fileName + ".jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, iceiumDataSource.getConnection());

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.reset();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".pdf\"");

        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        FacesContext.getCurrentInstance().responseComplete();
    }

    private void createSalesLineChartModel()
    {
        salesLineModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (List<OrderEntity> orderEntities : orderEntitiesList)
        {
            labels.add(orderEntities.get(0).getMonthYear());

            BigDecimal totalSales = BigDecimal.ZERO;

            for (OrderEntity orderEntity : orderEntities)
            {
                totalSales = totalSales.add(orderEntity.getTotalAmount());
            }

            values.add(totalSales);
        }

        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("");
        dataSet.setBorderColor(salesLineModelHex1);
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);

        data.setLabels(labels);

        //Options
        LineChartOptions options = new LineChartOptions();

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Monthly Revenue Report Chart");
        options.setTitle(title);

        CartesianScales cScales = new CartesianScales();

        CartesianLinearAxes xLinearAxes = new CartesianLinearAxes();
        xLinearAxes.setStacked(true);
        xLinearAxes.setOffset(true);

        CartesianLinearAxes yLinearAxes = new CartesianLinearAxes();
        yLinearAxes.setStacked(true);
        yLinearAxes.setOffset(true);

        CartesianScaleLabel xScaleLabel = new CartesianScaleLabel();
        xScaleLabel.setDisplay(true);
        xScaleLabel.setLabelString("Month Year");

        CartesianScaleLabel yScaleLabel = new CartesianScaleLabel();
        yScaleLabel.setDisplay(true);
        yScaleLabel.setLabelString("Amount ($)");

        xLinearAxes.setScaleLabel(xScaleLabel);
        yLinearAxes.setScaleLabel(yScaleLabel);

        cScales.addXAxesData(xLinearAxes);
        cScales.addYAxesData(yLinearAxes);

        options.setScales(cScales);

        salesLineModel.setOptions(options);
        salesLineModel.setData(data);
    }

    private void createOrdersStackedGroupModel()
    {
        ordersStackedGroupModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet ordersMadeDataSet = new BarChartDataSet();
        BarChartDataSet productsSoldDataSet = new BarChartDataSet();

        ordersMadeDataSet.setLabel("Monthly Orders Report Chart");
        ordersMadeDataSet.setBackgroundColor(ordersStackedGroupModelHex1);
        ordersMadeDataSet.setStack("Stack 0");

        productsSoldDataSet.setLabel("Products Sold");
        productsSoldDataSet.setBackgroundColor(ordersStackedGroupModelHex2);
        productsSoldDataSet.setStack("Stack 1");

        List<String> labels = new ArrayList<>();
        List<Number> ordersMadeDataVal = new ArrayList<>();
        List<Number> productsSoldDataVal = new ArrayList<>();

        for (List<OrderEntity> orderEntities : orderEntitiesList)
        {
            labels.add(orderEntities.get(0).getMonthYear());

            int ordersMade = 0;
            int productsSold = 0;

            for (OrderEntity orderEntity : orderEntities)
            {
                ordersMade++;
                productsSold += orderEntity.getTotalQuantity();
            }

            ordersMadeDataVal.add(ordersMade);
            productsSoldDataVal.add(productsSold);
        }

        ordersMadeDataSet.setData(ordersMadeDataVal);
        productsSoldDataSet.setData(productsSoldDataVal);

        data.addChartDataSet(ordersMadeDataSet);
        data.addChartDataSet(productsSoldDataSet);

        data.setLabels(labels);
        ordersStackedGroupModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();

        CartesianScales cScales = new CartesianScales();

        CartesianLinearAxes xLinearAxes = new CartesianLinearAxes();
        xLinearAxes.setStacked(true);
        xLinearAxes.setOffset(true);

        CartesianLinearAxes yLinearAxes = new CartesianLinearAxes();
        yLinearAxes.setStacked(true);
        yLinearAxes.setOffset(true);

        CartesianScaleLabel xScaleLabel = new CartesianScaleLabel();
        xScaleLabel.setDisplay(true);
        xScaleLabel.setLabelString("Month Year");

        CartesianScaleLabel yScaleLabel = new CartesianScaleLabel();
        yScaleLabel.setDisplay(true);
        yScaleLabel.setLabelString("Number");

        xLinearAxes.setScaleLabel(xScaleLabel);
        yLinearAxes.setScaleLabel(yScaleLabel);

        cScales.addXAxesData(xLinearAxes);
        cScales.addYAxesData(yLinearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Orders Chart");

        options.setTitle(title);

        Tooltip tooltip = new Tooltip();
        tooltip.setMode("index");
        tooltip.setIntersect(false);
        options.setTooltip(tooltip);

        ordersStackedGroupModel.setOptions(options);
    }

    private void createOrderFulfilmentPieModel()
    {
        orderFulfilmentPieModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        int delivered = 0;
        int cancelled = 0;
        int refundCompleted = 0;
        int exchangeCompleted = 0;

        List<OrderEntity> orderEntities = orderEntitiesList.get(orderEntitiesList.size() - 1);
        
        for (OrderEntity orderEntity : orderEntities)
        {
            for (OrderItemEntity orderItemEntity : orderEntity.getOrderItemEntities())
            {
                OrderItemStatusEnum orderItemStatusEnum = orderItemEntity.getOrderItemStatusEnum();
                if (orderItemStatusEnum.equals(OrderItemStatusEnum.DELIVERED))
                {
                    delivered++;
                }
                else if (orderItemStatusEnum.equals(OrderItemStatusEnum.CANCELLED))
                {
                    cancelled++;
                }
                else if (orderItemStatusEnum.equals(OrderItemStatusEnum.REFUND_COMPLETED))
                {
                    refundCompleted++;
                }
                else if (orderItemStatusEnum.equals(OrderItemStatusEnum.EXCHANGE_COMPLETED))
                {
                    exchangeCompleted++;
                }
            }
        }

        List<Number> values = new ArrayList<>();
        values.add(delivered);
        values.add(cancelled);
        values.add(refundCompleted);
        values.add(exchangeCompleted);
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add(orderFulfilmentPieModelHex1);
        bgColors.add(orderFulfilmentPieModelHex2);
        bgColors.add(orderFulfilmentPieModelHex3);
        bgColors.add(orderFulfilmentPieModelHex4);
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add(OrderItemStatusEnum.DELIVERED.getPrintName());
        labels.add(OrderItemStatusEnum.CANCELLED.getPrintName());
        labels.add(OrderItemStatusEnum.REFUND_COMPLETED.getPrintName());
        labels.add(OrderItemStatusEnum.EXCHANGE_COMPLETED.getPrintName());
        data.setLabels(labels);

        PieChartOptions options = new PieChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Order Fulfilment Chart for " + orderEntities.get(0).getMonthYear());

        options.setTitle(title);

        orderFulfilmentPieModel.setOptions(options);

        orderFulfilmentPieModel.setData(data);
    }

    private void createCustomerAgesDonutModel()
    {
        customerAgesDonutModel = new DonutChartModel();

        ChartData data = new ChartData();

        DonutChartDataSet dataSet = new DonutChartDataSet();

        int twelveBelow = 0;
        int thirteenSeventeen = 0;
        int eighteenTwentyFour = 0;
        int twentyFiveThirtyFour = 0;
        int thirtyFiveFortyFour = 0;
        int fortyFiveFiftyFour = 0;
        int fiftyFiveSixtyFour = 0;
        int sixtyFiveOver = 0;

        for (Map.Entry<Integer, Integer> entry : customerAges.entrySet())
        {
            int age = entry.getKey();
            int count = entry.getValue();
            if (0 <= age && age <= 12)
            {
                twelveBelow += count;
            }
            else if (13 <= age && age <= 17)
            {
                thirteenSeventeen += count;
            }
            else if (18 <= age && age <= 24)
            {
                eighteenTwentyFour += count;
            }
            else if (25 <= age && age <= 34)
            {
                twentyFiveThirtyFour += count;
            }
            else if (35 <= age && age <= 44)
            {
                thirtyFiveFortyFour += count;
            }
            else if (45 <= age && age <= 54)
            {
                fortyFiveFiftyFour += count;
            }
            else if (55 <= age && age <= 64)
            {
                fiftyFiveSixtyFour += count;
            }
            else if (65 <= age)
            {
                sixtyFiveOver += count;
            }
        }

        List<Number> values = new ArrayList<>();
        values.add(twelveBelow);
        values.add(thirteenSeventeen);
        values.add(eighteenTwentyFour);
        values.add(twentyFiveThirtyFour);
        values.add(thirtyFiveFortyFour);
        values.add(fortyFiveFiftyFour);
        values.add(fiftyFiveSixtyFour);
        values.add(sixtyFiveOver);
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add(customerAgesDonutModelHex1);
        bgColors.add(customerAgesDonutModelHex2);
        bgColors.add(customerAgesDonutModelHex3);
        bgColors.add(customerAgesDonutModelHex4);
        bgColors.add(customerAgesDonutModelHex5);
        bgColors.add(customerAgesDonutModelHex6);
        bgColors.add(customerAgesDonutModelHex7);
        bgColors.add(customerAgesDonutModelHex8);
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();
        labels.add("12 and below");
        labels.add("13-17");
        labels.add("18-24");
        labels.add("25-34");
        labels.add("35-44");
        labels.add("45-54");
        labels.add("55-64");
        labels.add("65 and over");
        data.setLabels(labels);

        //Options
        DonutChartOptions options = new DonutChartOptions();

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Customer Age Demographics Chart");
        options.setTitle(title);

        customerAgesDonutModel.setOptions(options);
        customerAgesDonutModel.setData(data);
    }

    public SelectItem[] getEnabledStatuses()
    {
        return this.enabledStatuses;
    }

    public AccessRightEnum[] getAccessRightEnums()
    {
        return AccessRightEnum.values();
    }

    public List<LocalDate> getSalesReportDates()
    {
        return salesReportDates;
    }

    public void setSalesReportDates(List<LocalDate> salesReportDates)
    {
        this.salesReportDates = salesReportDates;
    }

    public BigDecimal getLowerBound()
    {
        return lowerBound;
    }

    public void setLowerBound(BigDecimal lowerBound)
    {
        this.lowerBound = lowerBound;
    }

    public BigDecimal getUpperBound()
    {
        return upperBound;
    }

    public void setUpperBound(BigDecimal upperBound)
    {
        this.upperBound = upperBound;
    }

    public String getEnabledStatus()
    {
        return enabledStatus;
    }

    public void setEnabledStatus(String enabledStatus)
    {
        this.enabledStatus = enabledStatus;
    }

    public String getAccessRight()
    {
        return accessRight;
    }

    public void setAccessRight(String accessRight)
    {
        this.accessRight = accessRight;
    }

    public LineChartModel getSalesLineModel()
    {
        return salesLineModel;
    }

    public void setSalesLineModel(LineChartModel salesLineModel)
    {
        this.salesLineModel = salesLineModel;
    }

    public BarChartModel getOrdersStackedGroupModel()
    {
        return ordersStackedGroupModel;
    }

    public void setOrdersStackedGroupModel(BarChartModel ordersStackedGroupModel)
    {
        this.ordersStackedGroupModel = ordersStackedGroupModel;
    }

    public PieChartModel getOrderFulfilmentPieModel()
    {
        return orderFulfilmentPieModel;
    }

    public void setOrderFulfilmentPieModel(PieChartModel orderFulfilmentPieModel)
    {
        this.orderFulfilmentPieModel = orderFulfilmentPieModel;
    }

    public DonutChartModel getCustomerAgesDonutModel()
    {
        return customerAgesDonutModel;
    }

    public void setCustomerAgesDonutModel(DonutChartModel customerAgesDonutModel)
    {
        this.customerAgesDonutModel = customerAgesDonutModel;
    }

}
