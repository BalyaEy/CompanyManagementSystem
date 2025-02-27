package connectDB;

public class JDBCUtil {	
	// database de enum data tipleri ile bulunan 2 row için enum hazırlamak.
	public enum ShipmentStatus {
		READY, SHIPPED, DECLINED, APPROVED, PENDING, AWAITING_CONFIRMATION 
, COMPLETED
	}
	
	public enum PaymentMethod {
	    cash, card, cheque, bill, other;

	}
	
} 
    


