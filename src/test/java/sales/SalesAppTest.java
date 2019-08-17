package sales;

import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

public class SalesAppTest {
    public static final String[] HEADER_STRING_WHEN_TRUE = {"Sales ID", "Sales Name", "Activity", "Time"};
    public static final String[] HEADER_STRING_WHEN_FALSE = {"Sales ID", "Sales Name", "Activity", "Local Time"};
    public static final int MAX_ROW = 6;
    public static final String SALES_ID = "1";

    public Date yesterday(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        return calendar.getTime();
    }
    public Date tomorrow(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.getTime();
    }

    @Test
    public void testGenerateReport() throws ParseException {
        SalesDao salesDao = mock(SalesDao.class);
        SalesReportDao salesReportDao = mock(SalesReportDao.class);
        Sales sales = mock(Sales.class);
        EcmService ecmService = mock(EcmService.class);
        SalesActivityReport salesActivityReport = mock(SalesActivityReport.class);
        //SalesReportData salesReportData = mock(SalesReportData.class);
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date effectiveTo = tomorrow(new Date());
        Date effectiveFrom = yesterday(new Date());
        int maxRow = MAX_ROW;
        List<SalesReportData> reportDataList = new ArrayList<>();

        IntStream.range(0, maxRow).forEach(e -> {
            SalesReportData salesReportData = mock(SalesReportData.class);
            reportDataList.add(salesReportData);
            when(salesReportData.getType()).thenReturn("SalesReportData" + e);
        });
        when(salesDao.getSalesBySalesId(anyString())).thenReturn(sales);
        when(sales.getEffectiveFrom()).thenReturn(effectiveFrom);
        when(sales.getEffectiveTo()).thenReturn(effectiveTo);
        when(salesReportDao.getReportData(any())).thenReturn(reportDataList);
        when(salesActivityReport.toXml()).thenReturn("this is report");
//        ReflectionTestUtils.setField(salesApp, SalesApp.class, "salesDao", salesDao, SalesDao.class)

        SalesApp salesApp = spy(SalesApp.class);
        ReflectionTestUtils.setField(salesApp,SalesApp.class,"salesDao",salesDao,SalesDao.class);
        ReflectionTestUtils.setField(salesApp,SalesApp.class,"salesReportDao",salesReportDao,SalesReportDao.class);
        ReflectionTestUtils.setField(salesApp,SalesApp.class,"ecmService",ecmService,EcmService.class);
        doReturn(salesActivityReport).when(salesApp).generateReport(anyList(),anyList());
        salesApp.generateSalesActivityReport("DUMMY", maxRow, false, false);
       verify(ecmService, times(1)).uploadDocument(salesActivityReport.toXml());
    }

    @Test
    public void should_return_header_with_LocalTime_when_give_isNatTrade_is_false() {
        boolean isNatTrade = false;
        SalesApp salesApp = spy(SalesApp.class);
        List<String> result = salesApp.generateHeader(isNatTrade);
        Assert.assertEquals(Arrays.asList(HEADER_STRING_WHEN_FALSE), result);

    }

    @Test
    public void should_return_header_with_LocalTime_when_give_isNatTrade_is_true() {
        boolean isNatTrade = true;
        SalesApp salesApp = spy(SalesApp.class);
        List<String> result = salesApp.generateHeader(isNatTrade);
        Assert.assertEquals(Arrays.asList(HEADER_STRING_WHEN_TRUE), result);
    }

    @Test
    public void should_return_when_call_generateSalesActivityReport_with_salesId_is_null() {
        String salesId = null;
        SalesApp salesApp = spy(SalesApp.class);
        salesApp.generateSalesActivityReport(salesId, 6, true, true);
        verify(salesApp, times(0)).generateHeader(true);
    }

    @Test
    public void should_return_when_call_generateSalesActivityReport_with_today_after_sales_getEffectiveTo() throws ParseException {
        String salesId = SALES_ID;
        Sales sales = mock(Sales.class);
        SalesDao salesDao = mock(SalesDao.class);
        when(salesDao.getSalesBySalesId(anyString())).thenReturn(sales);
        Date EffectiveTo = yesterday(new Date());
        Date EffectiveFrom = yesterday(yesterday(new Date()));
        when(sales.getEffectiveTo()).thenReturn(EffectiveTo);
        when(sales.getEffectiveFrom()).thenReturn(EffectiveFrom);
        SalesApp salesApp = spy(SalesApp.class);
        ReflectionTestUtils.setField(salesApp, SalesApp.class, "salesDao", salesDao, SalesDao.class);

        salesApp.generateSalesActivityReport(salesId, MAX_ROW, true, true);
        verify(salesApp, times(0)).generateHeader(true);
    }

    @Test
    public void should_return_when_call_generateSalesActivityReport_with_today_before_sales_getEffectiveFrom() throws ParseException {
        String salesId = SALES_ID;
        Sales sales = mock(Sales.class);
        SalesDao salesDao = mock(SalesDao.class);
        when(salesDao.getSalesBySalesId(anyString())).thenReturn(sales);
        Date EffectiveTo = tomorrow(new Date());
        Date EffectiveFrom = tomorrow(new Date());
        when(sales.getEffectiveTo()).thenReturn(EffectiveTo);
        when(sales.getEffectiveFrom()).thenReturn(EffectiveFrom);
        SalesApp salesApp = spy(SalesApp.class);
        ReflectionTestUtils.setField(salesApp, SalesApp.class, "salesDao", salesDao, SalesDao.class);

        salesApp.generateSalesActivityReport(salesId, MAX_ROW, true, true);
        verify(salesApp, times(0)).generateHeader(true);
    }
}
