package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

    public static final String SALES_ACTIVITY = "SalesActivity";
    public static final String[] HEADER_STRING_WHEN_TRUE = {"Sales ID", "Sales Name", "Activity", "Time"};
    public static final String[] HEADER_STRING_WHEN_FALSE = {"Sales ID", "Sales Name", "Activity", "Local Time"};
    SalesDao salesDao = new SalesDao();
    SalesReportDao salesReportDao = new SalesReportDao();
    EcmService ecmService = new EcmService();

    public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {
        Date today = new Date();
        if (salesId == null) {
            return;
        }
        Sales sales = salesDao.getSalesBySalesId(salesId);
        if (today.after(sales.getEffectiveTo())
                || today.before(sales.getEffectiveFrom())) {
            return;
        }


        List<SalesReportData> filteredReportDataList = new ArrayList<>();

        List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);
//		generateFilteredReportDataList(isSupervisor, filteredReportDataList, reportDataList);

//		List<SalesReportData> tempList = new ArrayList<SalesReportData>();
//		for (int i=0; i < reportDataList.size() || i < maxRow; i++) {
//			tempList.add(reportDataList.get(i));
//		}
//		filteredReportDataList = tempList;


        List<String> headers = generateHeader(isNatTrade);

        SalesActivityReport report = this.generateReport(headers, reportDataList);

        ecmService.uploadDocument(report.toXml());

    }

    protected List<String> generateHeader(boolean isNatTrade) {
        List<String> headers;
        if (isNatTrade) {
            headers = Arrays.asList(HEADER_STRING_WHEN_TRUE);
        } else {
            headers = Arrays.asList(HEADER_STRING_WHEN_FALSE);
        }
        return headers;
    }

//	protected void generateFilteredReportDataList(boolean isSupervisor, List<SalesReportData> filteredReportDataList, List<SalesReportData> reportDataList) {
//		for (SalesReportData data : reportDataList) {
//			if (SALES_ACTIVITY.equalsIgnoreCase(data.getType())) {
//				if (data.isConfidential()) {
//					if (isSupervisor) {
//						filteredReportDataList.add(data);
//					}
//				}else {
//					filteredReportDataList.add(data);
//				}
//			}
//		}
//	}

    protected SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
        // TODO Auto-generated method stub
        return null;
    }

}
