package app;


public class LGA {
   // LGA Code
   private int code;

   // LGA Name
   private String name;

   // LGA Year
   private int year;

   /**
    * Create an LGA and set the fields
    */
   public LGA(int code, String name, int year) {
      this.code = code;
      this.name = name;
      this.year = year;
   }

   public int getCode() {
      return code;
   }

   public String getName() {
      return name;
   }

   public int getYear() {
      return year;
   }
}
