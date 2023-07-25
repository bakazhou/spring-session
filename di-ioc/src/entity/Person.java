package entity;

public class Person {
//    private Phone phone;
//
//    public Person(Phone phone) {
//        this.phone = phone;
//    }
//
//    public Phone getPhone() {
//        return phone;
//    }
//
//    public void setPhone(Phone phone) {
//        this.phone = phone;
//    }
//    public void call(String phoneNumber) {
//        phone.call(phoneNumber);
//    }

    public void call(String phoneNumber) {
        Phone phone = new Phone();
        phone.call(phoneNumber);
    }
}
