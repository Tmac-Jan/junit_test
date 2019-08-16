package parking;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mockit.Expectations;
import org.junit.Assert;
import org.junit.Test;

public class InOrderParkingStrategyTest {

	@Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    ParkingLot parkingLot = mock(ParkingLot.class);
	    when(parkingLot.getName()).thenReturn("ParkingLotName");
	    Car car = mock(Car.class);
	    when(car.getName()).thenReturn("CarName");
      Receipt receiptExpected = new Receipt();
      receiptExpected.setParkingLotName(parkingLot.getName());
      receiptExpected.setCarName(car.getName());

	    InOrderParkingStrategy inOrderParkingStrategy = mock(InOrderParkingStrategy.class);
	    when(inOrderParkingStrategy.createReceipt(parkingLot,car))
        .thenReturn(receiptExpected);
//	    Receipt receiptActual =


//      Assert.assertNotEquals(null,);
	    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
	    * With using Mockito to mock the input parameter */

//	    new Expectations()

    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */
        ParkingLot mparkingLot = mock(ParkingLot.class);
        when(mparkingLot.getName()).thenReturn("ParkingLotName");
        Car mcar = mock(Car.class);
        when(mcar.getName()).thenReturn("CarName");
        Receipt receiptExpected = new Receipt();
        receiptExpected.setParkingLotName(mparkingLot.getName());
        receiptExpected.setCarName(mcar.getName());

        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();

        Receipt receiptActual = inOrderParkingStrategy.createReceipt(mparkingLot,mcar);

        verify(mcar,times(0)).getName();
        Assert.assertEquals("CarName",receiptActual.getCarName());
    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt(){

	    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation
	    for no available parking lot */

        Car car = new Car("GioCar");
        InOrderParkingStrategy spyInOrderParkingStrategy = spy(InOrderParkingStrategy.class);

        Receipt receiptActual = spyInOrderParkingStrategy.park(new ArrayList<>(),car);

        verify(spyInOrderParkingStrategy,times(1)).park(new ArrayList<>(),car);
        Assert.assertEquals("GioCar",receiptActual.getCarName());
        Assert.assertEquals("No Parking Lot",receiptActual.getParkingLotName());
    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */

        InOrderParkingStrategy spyInOrderParkingStrategy = spy(InOrderParkingStrategy.class);
        Car car = new Car("GioCar");
        ParkingLot parkingLot = new ParkingLot("GioParkingLot",1);
        List<ParkingLot> parkingLotList = Arrays.asList(parkingLot);
        Receipt receipt = spyInOrderParkingStrategy.park(parkingLotList,car);

        verify(spyInOrderParkingStrategy,times(1)).
    createReceipt(parkingLot,car);
        Assert.assertEquals(car,parkingLot.getParkedCars().get(0));
//        Assert.assertNotEquals(null,receipt);
    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateNoReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */
        InOrderParkingStrategy spyInOrderParkingStrategy = spy(InOrderParkingStrategy.class);
        Car car = new Car("GioCar");
        Car otherCar = new Car("OtherCar");
        ParkingLot parkingLot = new ParkingLot("GioParkingLot",1);
        parkingLot.getParkedCars().add(otherCar);

        List<ParkingLot> parkingLotList = Arrays.asList(parkingLot);
        Receipt receipt = spyInOrderParkingStrategy.park(parkingLotList,car);
        verify(spyInOrderParkingStrategy,times(0)).createReceipt(parkingLot,car);
        Assert.assertEquals("GioCar",receipt.getCarName());
    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot(){

        /* Exercise 3: Test park() method.
        Use Mockito.spy and Mockito.verify to test the situation
         for multiple parking lot situation */
        InOrderParkingStrategy spyInOrderParkingStrategy = spy(InOrderParkingStrategy.class);
        Car car = new Car("GioCar");
        Car otherCar = new Car("OtherCar");
        ParkingLot parkingLot1 = new ParkingLot("GioParkingLot1",1);
        ParkingLot parkingLot2 = new ParkingLot("GioParkingLot2",1);
        parkingLot1.getParkedCars().add(otherCar);
        List<ParkingLot> parkingLotList = Arrays.asList(parkingLot1,parkingLot2);
        Receipt receipt = spyInOrderParkingStrategy.park(parkingLotList,car);
        verify(spyInOrderParkingStrategy,times(1)).createReceipt(parkingLot2,car);
        Assert.assertEquals("GioCar",receipt.getCarName());
        Assert.assertEquals("GioParkingLot2",receipt.getParkingLotName());
    }


}
