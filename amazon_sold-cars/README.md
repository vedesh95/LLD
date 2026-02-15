code: https://pastebin.com/FNuXyXUX
```java
/*  
buy and sell cars  
view car details  
search for cars by various search criteria  
  
---  
 */  
/*  
* user -  
* userType - buyer seller  
* car  
* listing  
* marketplace  
* search cars based on some criteria  
* */  
  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
  
enum UserType{  
    BUYER,  
    SELLER  
}  
class User{  
    private String id;  
    ...  
    // getter and setters  
}  
class Car{  
    private String id;  
    private String color;  
    private String brand;  
    double price;  
  
    // getter and setters  
}  
enum STATUS{  
    LISTED,  
    SOLD  
}  
class Listing{  
    private String id;  
    private Car car;  
    private User buyer;  
    private User seller;  
    private STATUS status;  
  
    Listing(Car car, User seller){  
        this.car = car;  
        this.seller = seller;  
        this.status = STATUS.LISTED;  
    }  
    // getter and setters  
  
}  
  
enum SearchCriteria {  
    SEARCHBYBRAND,  
    SEARCHBYCOLOR,  
    SEARCHBYBRANDANDCOLOR  
}  
  
interface Search{  
    List<Car> search(List<Car> cars);  
}  
  
class SearchByBrand implements Search{  
    static List<Car> search(List<Car> cars){  
        // logic here  
    }  
}  
  
  
class MarketPlace{  
    Map<String, Listing> listings;  
    Map<String, Listing> sold;  
    MarketPlace(){  
        listings = new HashMap<>();  
    }  
  
    boolean addListing(Car car, User seller){  
        if(listings.containsKey(car.getId())) return 0;  
        listings.put(car.getId(), new Listing(car, seller));  
    }  
  
    boolean purchaseCar(String listingId, User buyer){  
        // logic for purchase  
    }  
  
    List<Car> searchCars(SearchCriteria criteria){  
        switch (criteria) {  
            case SEARCHBYBRAND:  
                return new SearchByBrand().search(getAvailableCars());  
            case SEARCHBYCOLOR:  
                return new SearchByColor().search(getAvailableCars());  
            ...  
            default:  
                return getAvailableCars();  
        }  
    }  
}
```
