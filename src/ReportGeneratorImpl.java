public class ReportGeneratorImpl implements ReportGenerator{

    private final OrderManagerImpl orderManager;

    public ReportGeneratorImpl(){
        this.orderManager = OrderManagerImpl.getInstance();
    }

    @Override
    public void generateDailySalesReport() {

    }
}
