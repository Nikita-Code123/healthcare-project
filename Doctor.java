import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctor extends Admin {
    String email, password,doct;
    boolean flag = false;

    public void LoginDoct() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("enter your email");
            email = br.readLine();
            System.out.println("enter your password");
            password = br.readLine();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("Select email,password from doctorlist");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String mail = rs.getString("email");
                String pass = rs.getString("password");
                if (email.equals(mail)) {
                    if (password.equals(pass)) {
                        flag = true;
                    } else {
                        System.out.println("Wrong Password");
                    }
                }
            }
            if (flag == true) {
                System.out.println("Login Successfully");
                System.out.println();
                DisplayD();
            } else {
                System.out.println("User not found");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void DisplayD(){
        try{
            int choice;
            do{
                System.out.println("1. View Profile ");
                System.out.println("2. View Patients List");
                System.out.println("3. View Appointment");
                System.out.println("4. Attend Patients");
                System.out.println("5. Logout");
                System.out.println("enter your choice");
                Scanner s=new Scanner(System.in);
                choice=s.nextInt();
                switch (choice) {
                    case 1:ViewProfileD();
                    break;
                    case 2:PatientProfile();
                    break;
                    case 3:ViewAppintments();
                    break;
                    case 4:attendPatients();
                    break;
                    case 5:System.out.println("Log out.....");
                    System.out.println("THANK YOU !");
                    break;
                    default:System.out.println("Invalid input");
                    break;
                }
            }
            while(choice!=5);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void ViewProfileD() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps = con.prepareStatement("Select * from doctorlist");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String mail1 = rs.getString("email");
                if (email.equals(mail1)) {
                    System.out.println("********************************");
                    System.out.println("           Your Profile");
                    System.out.println("Name :"+rs.getString("name"));
                    System.out.println("Specialization"+rs.getString("specialization"));
                    System.out.println("Contact :"+rs.getString("contact"));
                    System.out.println("********************************");
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void ViewAppintments() {
        int c=0;
        try{
            System.out.println("Enter your name Doctor ");
            Scanner s=new Scanner(System.in);
            doct=s.nextLine();
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps=con.prepareStatement("Select *from Appointment");
            ResultSet rs=ps.executeQuery();
          
            while (rs.next()) {
                String n=rs.getString("Doctor");
                if(doct.equals(n)){
                    System.out.println("----------Your appointments are with these patients----------");
                    System.out.println();
                    System.out.println("Patient Name :"+rs.getString("Patient_name"));
                    System.out.println("Timings :"+rs.getString("dateandtime"));
                    System.out.println("Illness :"+rs.getString("illness"));
                    System.out.println("Patient_email :"+rs.getString("email"));
                    
                    c++;
                }  }
                if(c==1){
                    System.out.println();
                } 
                else {
                    System.out.println("You have no Appointmnet at these time");
                } 
            
        }catch(Exception e){System.out.println(e);}

    }

    public void attendPatients() {
        try{
            int count=0;
            ViewAppintments();
            String a="Attended";
            System.out.println("These are your apointment for attend patient you have to type patient name");
           
            Scanner s=new Scanner(System.in);
            String pname=s.nextLine();
          //  System.out.println("Enter your name");
          //  String doct1=s.nextLine();
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE","root","nikki0623");
            PreparedStatement ps=con.prepareStatement("Update  Appointment set appointment_position =? where Patient_name=?");
            ps.setString(1, a);
            ps.setString(2, pname);
            int i=ps.executeUpdate();
            if(i>0){
                System.out.println("OK Done");
                MakeReport();
                
                count++;
            }
            else{
                System.out.println("Something went wrong please try again");
            }
        
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void MakeReport(){
        try{
            String payment="pending";
            Scanner s=new Scanner(System.in);
            System.out.println("Make Report of your Patient");
            System.out.println();
            System.out.println("Fill the Information of your patient");
            System.out.println("Enter Patient name");
            String name=s.nextLine();
            System.out.println("enter patient email");
            String mail=s.nextLine();
            System.out.println("Suggest cure ");
            String cure =s.nextLine();
            System.out.println("Patient_Disease");
            String ill=s.nextLine();
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/HEALTHCARE", "root", "nikki0623");
            PreparedStatement ps=con.prepareStatement("insert into Report values (?,?,?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, mail);
            ps.setString(3, doct);
            ps.setString(4, cure);
            ps.setString(5, payment);
            ps.setString(6, ill);
            int i=ps.executeUpdate();
            if(i>0){
                System.out.println("Report Made Successfully");
            }
            else{
                System.out.println("Try again");
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
      //   Doctor d=new Doctor();
      //  d.attendPatients();
     // d.ViewAppintments();
    }
}
