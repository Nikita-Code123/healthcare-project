import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Patients extends Doctor {
    int payment = 0, report = 0;
    String name, age, phone, address, password, gender, illness, Feedback, email, doctor, date_time;
    boolean flag = false;

    public void SignUp() {// ..................Sign Up Method (for Patient)
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter Your Details");// Sign up code
            System.out.println(" Your full Name");
            name = br.readLine();
            System.out.println("your email");
            email = br.readLine();
            System.out.println("Your Age");
            age = br.readLine();
            System.out.println("Your phone number");
            phone = br.readLine();
            System.out.println("your Address");
            address = br.readLine();
            System.out.println("make password");
            password = br.readLine();
            System.out.println("Gender");
            gender = br.readLine();
            System.out.println("Dieases");
            illness = br.readLine();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("insert into PatientDetails values (?,?,?,?,?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, age);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setString(5, gender);
            ps.setString(6, illness);
            ps.setString(7, password);
            ps.setString(8, email);
            int i = ps.executeUpdate();
            if (i > 0) {
                System.out.println("         succussfully sign up");
                System.out.println("NOTE:-Remember your email and password for login");
                System.out.println();
                System.out.println("                Welcome to + Health Care + ");
                System.out.println("           Now your are the memeber of Health Care");
                System.out.println(" --------------------- Thank You ---------------------");
                displayOption();
            } else {
                System.out.println("something went wrong please try again latar");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void LoginP() {// ...............Login by Patient method...........................
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter your email");
            email = br.readLine();
            System.out.println("enter Password");
            password = br.readLine();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("Select email,password from PatientDetails");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String email1 = rs.getString("email");
                String pass = rs.getString("password");
                if (email.equals(email1)) {
                    if (pass.equals(password)) {

                        flag = true;

                        break;
                    } else {
                        System.out.println("wrong password");
                        break;
                    }
                }
            }
            if (flag == true) {
                System.out.println("login Successfully");
                displayOption();
            } else {
                System.out.println("user not found");
                //
                LoginP();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void displayOption() {// .............options for Patients ...........................................
        int option;
        do {
            System.out.println("1. View Profile");
            System.out.println("2. View Doctors");
            System.out.println("3. Book Appointment");
            System.out.println("4. View Report");
            System.out.println("5. Choose Doctor");
            System.out.println("6. View Appointment");
            System.out.println("7. Give Feedback");
            System.out.println("8. Pay Online ");
            System.out.println("9. Logout");
            Scanner s = new Scanner(System.in);
            System.out.println("enter your  choice");
            option = s.nextInt();

            switch (option) {
                case 1:
                    ViewProfileP();
                    break;
                case 2:
                    ViewDoctorList();
                    break;
                case 3:
                    BookAppointment();
                    break;
                case 4:
                    ViewReport();
                    break;
                case 5:
                    ChooseDoctor();
                    break;
                case 6:
                    ViewAppointment();
                    break;
                case 7:
                    giveFeedback();
                    break;
                case 8:
                    payOnline();
                    break;
                case 9:

                    System.out.println("logout....");
                    System.out.println("Thank You! for using our app ! Stay Happy and Healthy");

                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while (option != 9);
    }

    public void giveFeedback() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter Your Name");
            name=br.readLine();
            System.out.println("Give your Feedbacks (word Limit 250)");
            Feedback = br.readLine();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("insert into FeedBacks values(?,?)");
            ps.setString(1, name);
            ps.setString(2, Feedback);
            int i = ps.executeUpdate();
            if (i > 0) {
                System.out.println("Thank you for your Feedback");
            } else {
                System.out.println("Something went wrong please try again");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ViewProfileP() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("Select * from PatientDetails");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String email3 = rs.getString("email");
                if (email.equals(email3)) {
                    System.out.println("**********************************");
                    System.out.println("          Your Profile");
                    System.out.println("Patient Name :"+rs.getString("name"));
                    System.out.println("Age :"+rs.getString("age"));
                    System.out.println("Contact no. :"+rs.getString("phone"));
                    System.out.println("Address :"+rs.getString("address"));
                    System.out.println("Gender :"+rs.getString("gender"));
                    System.out.println("Disease :"+rs.getString("illness"));
                    System.out.println("***********************************");
                }
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ChooseDoctor() {
        try {
            Scanner s = new Scanner(System.in);
            // System.out.println("Enter your name");
            // String name1=s.nextLine();

            ViewDoctorList();
            System.out.println("Choose Doctor");
            doctor = s.nextLine();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("update  Appointment set Doctor =? where email=?");
            ps.setString(1, doctor);
            ps.setString(2, email);
            int i = ps.executeUpdate();
            if (i > 0) {
                System.out.println("thank you for choosing our best doctor");
            } else {
                System.out.println("Something went wrong");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void BookAppointment() {
        try {
            String p = "pending";
            System.out.println("--------------For book appointment you have to fill the information---------------");
            System.out.println("your name (Official which is register )");
            Scanner s = new Scanner(System.in);
            name = s.nextLine();
            System.out.println("Choose Doctor");
            ViewDoctorList();
            doctor = s.nextLine();
            System.out.println("enter date and time (yy-mm-dd hh:mm:ss) ");
            date_time = s.nextLine();
            System.out.println("Illness");
            illness = s.nextLine();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("insert into Appointment values(?,?,?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, doctor);
            ps.setString(3, date_time);
            ps.setString(4, illness);
            ps.setString(5, p);
            ps.setString(6, email);

            int i = ps.executeUpdate();
            if (i > 0) {
                System.out.println("Successfully booked Appointment to your doctor :" + doctor);
            } else {
                System.out.println("something went wrong ");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ViewAppointment() {
        int cout = 0;
        try {
            Scanner s = new Scanner(System.in);
            //System.out.println("enter your name");
            //String name1 = s.nextLine();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("Select *from Appointment");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String pemail = rs.getString("email");
                if (email.equals(pemail)) {
                    System.out.println("----------------Your Appointment----------------------");
                    System.out.println();
                    System.out.println("Patient name :" + rs.getString("Patient_name"));
                    System.out.println("Patient_email :" + rs.getString("email"));
                    System.out.println("Your Doctor :"+rs.getString("Doctor"));
                    System.out.println("Timings :"+rs.getString("DateandTime"));
                    System.out.println("Illness :"+rs.getString("illness"));
                    System.out.println("Appointment Status :"+rs.getString("appointment_position"));
                    cout++;
                }
            }
            if (cout >0) {
                System.out.println();

            } else {
                System.out.println("You have not any appointmnet till now");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ViewReport() {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("Select * from Report");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String mail = rs.getString("email");
                if (email.equals(mail)) {
                    System.out.println("-----------------Your Report ----------------");

                    ViewProfileP();
                    System.out.println("Your Doctor :" + rs.getString("Doctor"));
                    System.out.println("Cure and Mediciences :" + rs.getString("cure"));
                    System.out.println("Payment :" + rs.getString("payment"));
                    report++;
                }
            }
            if (report == 1) {
                System.out.println("-----------------------------------------");
            } else {
                System.out.println("Your report is not ready yet.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void payOnline() {
        try {
            // int r=0;
            // Scanner s = new Scanner(System.in);
            // System.out.println(" Check your Payment");

            //
            ViewReport();

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("Select *from Report");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String mail = rs.getString("email");
                if (email.equals(mail)) {
                    String fee = rs.getString("payment");
                    if (fee.equals("pending")) {
                        Payment();
                    } else {
                        System.out.println("You already paid your payment ");
                    }
                }
                // System.out.println("logout....");
                // System.out.println("Thank You! for using our app ! Stay Happy and Healthy");

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Payment() {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("For Online Payment .....");
            System.out.println("Fill appropriate details");
            System.out.println("enter your Transaction Id");
            String ID = s.nextLine();
            System.out.println("Your Payment is 500 Rs");
            int fees = s.nextInt();
            if (fees == 500) {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root",
                        "nikki0623");
                PreparedStatement ps = con.prepareStatement("update Report set payment =? where email=?");
                ps.setInt(1, fees);
                ps.setString(2, email);
                int i = ps.executeUpdate();
                if (i > 0) {
                    System.out.println("Payment Successfully " + ID);
                    System.out.println("THANK YOU !");
                } else {
                    System.out.println("Transaction failed");
                }
            } else {
                System.out.println("Invalid Amount ");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            Scanner s = new Scanner(System.in);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Patients p = new Patients();
            System.out.println("----------------------------------------------------------------");
            System.out.println("                   Welcome To Health Care ");
            System.out.println();

            System.out.println("                 1. Login         2.Sign up");
            System.out.println("-----------------------------------------------------------------");
            int n = 0;
            try {
                n = s.nextInt();
            } catch (Exception e) {
                System.out.println("Invaild Input");
            }
            if (n == 1) {
                System.err.println("Login as.........");
                System.out.println("1. Admin ");
                System.out.println("2. Doctor");
                System.out.println("3. Patient");
                int log = s.nextInt();
                if (log == 1) {
                    p.LoginAdmin();// admin login method

                } else if (log == 2) {
                    p.LoginDoct();// doctor login method

                } else if (log == 3) {
                    p.LoginP(); // patient login method

                } else {
                    System.out.println("Invalid input");
                }
            } else if (n == 2) {
                p.SignUp();
            } else {
                System.out.println("Invalid input");

            }
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }
}
