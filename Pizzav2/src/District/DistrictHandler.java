package District;

import java.util.ArrayList;
import java.util.List;

public class DistrictHandler {
    //TODO REFACTOR
    static List<District> districts = new ArrayList<>();

    public static void getOptions() {
        for (District dist :
                districts) {
            System.out.println(dist.toString());
        }
    }

    public static void addDistrict(String title, String description) {
        District district = new District(title, description);
        if (districts.contains(district)) {
            System.out.println("Уже существует!");
        } else {
            districts.add(district);
        }
    }

    public static void addDistrict(District district) {
        if (districts.contains(district)) {
            System.out.println("Уже существует!");
        } else {
            districts.add(district);
        }
    }

    public static void removeDistrict(District district) {
        if (!districts.contains(district)) {
            System.out.println("Не существует!");
            return;
        }
        districts.remove(district);
    }

    public static District getDistrict(String title) {
        for (District dist :
                districts) {
            if (dist.getTitle().equals(title)) {
                return dist;
            }
        }
        return null;
    }

    public static int getIndex(District district){
        return districts.indexOf(district);
    }

    public static District getDistrict(int index){
        if (index > districts.size() || index < 0) {
            System.out.println("Неккоректный индекс");
            return null;
        }
        return districts.get(index);
    }
}
