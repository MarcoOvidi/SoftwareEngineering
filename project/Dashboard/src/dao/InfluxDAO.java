package dao;
//import org.influxdb.BatchOptions;
//import org.influxdb.InfluxDBFactory;
//import org.influxdb.annotation.Column;
//import org.influxdb.annotation.Measurement;
//import org.influxdb.dto.*;
//import org.influxdb.impl.InfluxDBResultMapper;
//
//
//import java.sql.Time;
//import java.time.Instant;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//
//
public class InfluxDAO {
//	public static void getLog(){
//		Query q = new Query("SELECT * from \"values\"","valuesStorage");
//		QueryResult qr = influxDB.query(q);
//		QueryResult.Series series = qr.getResults().get(0).getSeries().get(0);
//
//
//		Iterator itr = series.getValues().iterator();
//
//		while(itr.hasNext()){
//		System.out.println(itr.next());
//		//fateci quello che vi serve
//		}
//		}
//
//		public static void getLog(int sensID) {
//		Query q = new Query("SELECT * FROM \"values\" WHERE \"sensorID\" = \'" + sensID + "\';", "valuesStorage");
//		QueryResult qr = influxDB.query(q);
//		QueryResult.Series series = qr.getResults().get(0).getSeries().get(0);
//
//		Iterator itr = series.getValues().iterator();
//
//		while(itr.hasNext()){
//		System.out.println(itr.next());
//		}
//
//		}
//
//		public static void getLogOverThreshold(int sensID) {
//		Query q = new Query("SELECT * FROM \"values\" WHERE \"sensorID\" = \'" + sensID + "\' AND \"currentThreshold\" <= \"value\";", "valuesStorage");
//		QueryResult qr = influxDB.query(q);
//
//		QueryResult.Series series = qr.getResults().get(0).getSeries().get(0);
//
//		Iterator itr = series.getValues().iterator();
//
//		while (itr.hasNext()) {
//		System.out.println(itr.next());
//		}
//
//		}
}
