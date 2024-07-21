import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Admin {
    String name = "Nikita Sahu";
    String Name, doctName, specialization, docNum;
    String password = "0623";
    String pass, email;

    public void LoginAdmin() {// ...................login method for admin.........................
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("enter your name");
            Name = r.readLine();
            System.out.println("enter your password");
            pass = r.readLine();
            if (Name.equals(name)) {
                if (pass.equals(password)) {
                    System.out.println("Login successfull");
                    Display();
                } else {
                    System.out.println("wrong password");
                }
            } else {
                System.out.println("User not found");
                LoginAdmin();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Display() {// ........................options for admin......................
        int opt;
        do {

            System.out.println("1. Add Doctors");
            System.out.println("2. View Patients");
            System.out.println("3. View Doctor List");
            System.out.println("4. Remove Doctor");
            System.out.println("5. See Feedback");
            System.out.println("6. View Reports");
            System.out.println("7. Logout");
            Scanner s = new Scanner(System.in);
            opt = s.nextInt();
            switch (opt) {
                case 1:
                    AddDoctor();
                    break;
                case 2:
                    PatientProfile();
                    break;
                case 3:
                    ViewDoctorList();
                    break;
                case 4:
                    RemoveDoctor();
                    break;
                case 5:
                    ViewFeedBack();
                    break;
                case 6:viewReports();
                    break;
                    case 7:System.out.println("Log out.....");
                    break;
                default:
                    System.out.println("Ivalid input");
                    break;
            }
        } while (opt != 7);
    }

    public void PatientProfile() {// .........................patient profiles method..........................
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("Select *from PatientDetails");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name1 = rs.getString("name");
                System.out.println("Patient Name :" + name1);
                String age1 = rs.getString("age");
                System.out.println("Patient Age :" + age1);
                System.out.println(rs.getString("email"));
                String phon1 = rs.getString("phone");
                System.out.println(" Patient Phone number :phon1");
                String address1 = rs.getString("address");
                System.out.println("Patient Address :" + address1);
                String gender1 = rs.getString("gender");
                System.out.println("Gender :" + gender1);
                String illness1 = rs.getString("illness");
                System.out.println("Illness :" + illness1);
                System.out.println();
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void AddDoctor() {// ....................method for adding doctors...............................
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter Doctor Name");
            doctName = br.readLine();
            System.out.println("Enter Specialization");
            specialization = br.readLine();
            System.out.println("enter Doctor's Phone number");
            docNum = br.readLine();
            System.out.println("Enter Email");
            email = br.readLine();
            System.out.println("Make password ");
            pass = br.readLine();

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("insert into DoctorList values(?,?,?,?,?)");
            ps.setString(1, doctName);
            ps.setString(2, specialization);
            ps.setString(3, docNum);
            ps.setString(4, email);
            ps.setString(5, pass);
            int i = ps.executeUpdate();
            if (i > 0) {
                System.out.println("Adding One more Doctor Successfully");
            } else {
                System.out.println("Something went wrong please try again");
            }
        } catch (Exception e) {
            System.out.println("Try Again");
        }
    }

    public void ViewDoctorList() {// .....................view doctor list method.....................
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("Select * from DoctorList");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String name = rs.getString("name");
                System.out.println("Doctor Name :" + name);
                String special = rs.getString("specialization");
                System.out.println("Specialization :" + special);
                String contact = rs.getString("contact");
                System.out.println("Contact number :" + contact);
                System.out.println("Dr. Email :"+rs.getString("email"));
                System.out.println();
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Try Again");
        }
    }
    public void RemoveDoctor() {// ..................remove doctor method..........................
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter Doctor email whom you want to remove");
            String email = br.readLine();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("delete from Doctorlist where email=?");
            ps.setString(1, email);
            int i = ps.executeUpdate();
            if (i > 0) {
                System.out.println("Successfully Removed");
            } else {
                System.out.println("failed");
            }
        } catch (Exception e) {
            System.out.println("Try Again");
        }
    }

    public void ViewFeedBack() {// ............method for view feedback given by patients............
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps=con.prepareStatement("Select * from feedbacks");
            ResultSet rs=ps.executeQuery();
            System.out.println("-----------------FeedBacks-----------------------");
            while (rs.next()) {
                System.out.println("Name :"+rs.getString("name"));
                System.out.println("Feedback :"+rs.getString("feedback"));  
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Try Again");
        }
    }

    public void viewReports() {//...............working.........
        try{
            Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps=con.prepareStatement("Select *from Report");
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                System.out.println("--------------------------------------------------------------------------------");
                System.out.println("                       "+rs.getString("Patient_name"));
                System.out.println("Patient email :"+rs.getString("email"));
                System.out.println("Doctor :"+rs.getString("Doctor"));
                System.out.println("Cure :"+rs.getString("cure"));
                System.out.println("Disease :"+rs.getString("illness"));
                System.out.println("Payment :"+rs.getString("payment"));
                System.out.println();
                
            }
        }
        catch(Exception e){
        System.out.println("Try Again");
    }
}

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }
}