package parking;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class VipParkingStrategyTest {

	@Test
    public void testPark_givenAVipCarAndAFullParkingLot_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 4, Write a test case on VipParkingStrategy.park()
	    * With using Mockito spy, verify and doReturn */
      ParkingLot parkingLot = new ParkingLot("GioParkingLot",1);
      parkingLot.getParkedCars().add(new Car("OtherCar"));
      List<ParkingLot> parkingLotList = Arrays.asList(parkingLot);
      Car vipCar = new Car("AVipCar");

	    VipParkingStrategy vipParkingStrategy = spy(VipParkingStrategy.class);
	    doReturn(true).when(vipParkingStrategy).isAllowOverPark(vipCar);
	    Receipt receipt = vipParkingStrategy.park(parkingLotList,vipCar);
	    Assert.assertEquals("AVipCar",receipt.getCarName());
      Assert.assertEquals("GioParkingLot",receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
        ParkingLot parkingLot = new ParkingLot("GioParkingLot",1);
        parkingLot.getParkedCars().add(new Car("OtherCar"));
        List<ParkingLot> parkingLotList = Arrays.asList(parkingLot);
        Car Car = new Car("BCar");

        VipParkingStrategy vipParkingStrategy = spy(VipParkingStrategy.class);
        doReturn(false).when(vipParkingStrategy).isAllowOverPark(Car);
        Receipt receipt = vipParkingStrategy.park(parkingLotList,Car);
        Assert.assertEquals("BCar",receipt.getCarName());
        Assert.assertEquals("No Parking Lot",receipt.getParkingLotName());

    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class),
         *  @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        ParkingLot parkingLot = new ParkingLot("GioParkingLot",1);
//        parkingLot.getParkedCars().add(new Car("OtherCar"));
        List<ParkingLot> parkingLotList = Arrays.asList(parkingLot);
        Car car = new Car("ACar");
        CarDaoImpl carDao = mock(CarDaoImpl.class);
        when(carDao.isVip("ACar")).thenReturn(true);
        VipParkingStrategy vipParkingStrategy = spy(VipParkingStrategy.class);
        doReturn(carDao).when(vipParkingStrategy).getCarDao();
        boolean allowOverPark = vipParkingStrategy.isAllowOverPark(car);
        Assert.assertTrue(allowOverPark);


    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        ParkingLot parkingLot = new ParkingLot("GioParkingLot",1);
        parkingLot.getParkedCars().add(new Car("OtherCar"));
        List<ParkingLot> parkingLotList = Arrays.asList(parkingLot);
        Car car = new Car("BCar");
        CarDaoImpl carDao = mock(CarDaoImpl.class);
        when(carDao.isVip("GioCar")).thenReturn(false);
        VipParkingStrategy vipParkingStrategy = spy(VipParkingStrategy.class);
        doReturn(carDao).when(vipParkingStrategy).getCarDao();
        boolean allowOverPark = vipParkingStrategy.isAllowOverPark(car);
        Assert.assertFalse(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse(){
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        ParkingLot parkingLot = new ParkingLot("GioParkingLot",1);
        parkingLot.getParkedCars().add(new Car("OtherCar"));
        List<ParkingLot> parkingLotList = Arrays.asList(parkingLot);
        Car car = new Car("ACar");
        CarDaoImpl carDao = mock(CarDaoImpl.class);
        when(carDao.isVip("ACar")).thenReturn(false);
        VipParkingStrategy vipParkingStrategy = spy(VipParkingStrategy.class);
        doReturn(carDao).when(vipParkingStrategy).getCarDao();
        boolean allowOverPark = vipParkingStrategy.isAllowOverPark(car);
        Assert.assertFalse(allowOverPark);

    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        ParkingLot parkingLot = new ParkingLot("GioParkingLot",1);
        parkingLot.getParkedCars().add(new Car("OtherCar"));
        List<ParkingLot> parkingLotList = Arrays.asList(parkingLot);
        Car car = new Car("BCar");
        CarDaoImpl carDao = mock(CarDaoImpl.class);
        when(carDao.isVip("BCar")).thenReturn(false);
        VipParkingStrategy vipParkingStrategy = spy(VipParkingStrategy.class);
        doReturn(carDao).when(vipParkingStrategy).getCarDao();
        boolean allowOverPark = vipParkingStrategy.isAllowOverPark(car);
        Assert.assertFalse(allowOverPark);
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
