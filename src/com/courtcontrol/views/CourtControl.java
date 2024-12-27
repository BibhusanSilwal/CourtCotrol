/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.courtcontrol.views;

import com.courtcontrol.controllers.datastructures.CustomQueue;
import com.courtcontrol.models.CustomerModel;
import com.courtcontrol.models.NoticeModel;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
 
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bibhu
 */
public class CourtControl extends javax.swing.JFrame {
    private ArrayList<CustomerModel> bookingList;
    private CustomQueue requestQueue;
    private ArrayList<NoticeModel> noticeList;
    private java.awt.CardLayout cardLayout;
    
    public CourtControl() {
        initComponents();
        initializeLayout();
        initializeData();
        
    }
    // Method to Add Customer Booking Request 
    public void addRequest(CustomerModel request){
        requestQueue.enqueue(request);
        
        DefaultTableModel model = (DefaultTableModel) tblBookingRequest.getModel();
    model.addRow(new Object[]{
        request.getCustomerId(), request.getCustomerName(), request.getCustomerPhone(), request.getCustomerCourtNo(),
        request.getCustomerDate(), request.getCustomerTime()
    });
    DefaultTableModel model2 = (DefaultTableModel) tblBookingCardRequest.getModel();
    model2.addRow(new Object[]{
        request.getCustomerId(), request.getCustomerName(), request.getCustomerPhone(), request.getCustomerCourtNo(),
        request.getCustomerDate(), request.getCustomerTime()
    });
    }
    
    // Method to Delete Customer Booking Request
    public void deleteRequest(){
        if(requestQueue.isEmpty()){
            System.out.print("The queue is empty");
            return;
        }
        
        int id = requestQueue.peek().getCustomerId();
        requestQueue.dequeue();
        DefaultTableModel model = (DefaultTableModel) tblBookingRequest.getModel();
        DefaultTableModel model2 = (DefaultTableModel) tblBookingCardRequest.getModel();
        for(int i = 0; i< model.getRowCount();i++){
            Object Id = model.getValueAt(i,0);
            if(Id instanceof Integer && (Integer) Id == id) {
                model.removeRow(i);
                model2.removeRow(i);
              return;
           }
        }
        
    }
    
    // Method to add booking in admin side
    
    public void addBooking(CustomerModel booking){
        bookingList.add(booking);
        lblRequestCounter.setText(String.valueOf(bookingList.size()));
        DefaultTableModel model = (DefaultTableModel) tblBookings.getModel();
    model.addRow(new Object[]{
        booking.getCustomerId(), booking.getCustomerName(), booking.getCustomerPhone(), booking.getCustomerCourtNo(),
        booking.getCustomerDate(), booking.getCustomerTime()
    });
    }
    
    // Method to update booking in admin side
    
    public void updateBooking(CustomerModel updatedBooking) {
    DefaultTableModel model = (DefaultTableModel) tblBookings.getModel();
    
    for (int i = 0; i < model.getRowCount(); i++) {
      
        int customerIdInTable = (int) model.getValueAt(i, 0);
        
        
        if (customerIdInTable == updatedBooking.getCustomerId()) {
            model.setValueAt(updatedBooking.getCustomerName(), i, 1); 
            model.setValueAt(updatedBooking.getCustomerPhone(), i, 2); 
            model.setValueAt(updatedBooking.getCustomerCourtNo(), i, 3); 
            model.setValueAt(updatedBooking.getCustomerDate(), i, 4); 
            model.setValueAt(updatedBooking.getCustomerTime(), i, 5); 
            break; 
        }
    }   
   
    for (int i = 0; i < bookingList.size(); i++) {
        if (bookingList.get(i).getCustomerId() == updatedBooking.getCustomerId()) {
            bookingList.set(i, updatedBooking);
            break;
        }
    }
}
    
    // Method to delete Bookings made by Admin
public void deleteBooking(int customerId) {
    DefaultTableModel model = (DefaultTableModel) tblBookings.getModel();
    
    for (int i = 0; i < model.getRowCount(); i++) {
        int customerIdInTable = (int) model.getValueAt(i, 0);
        if (customerIdInTable == customerId) {
            model.removeRow(i);
            break; 
        }
    }
    
  
    for (int i = 0; i < bookingList.size(); i++) {
        if (bookingList.get(i).getCustomerId() == customerId) {
            bookingList.remove(i);
            break;
        }
    }
    lblRequestCounter.setText(String.valueOf(bookingList.size()));
}

    //Method to add notice by admin for customers
public void addNotice(NoticeModel notice){
    noticeList.add(notice);
    updateNoticeList();
}
public void updateNotice(NoticeModel notice){
 
    for (int i = 0; i < noticeList.size(); i++) {
        if (noticeList.get(i).getNoticeId() == notice.getNoticeId()) {
            noticeList.set(i, notice);
            break;
        }
    }
   updateNoticeList();
}

public void deleteNotice(int noticeId) { 
  
    for (int i = 0; i < noticeList.size(); i++) {
        if (noticeList.get(i).getNoticeId() == noticeId) {
            noticeList.remove(i);
            break;
        }
    }
    updateNoticeList();
}


private void updateNoticeList(){
        DefaultListModel<String> model = new DefaultListModel<>();   
    for (NoticeModel n : noticeList) {
        String formattedNotice = String.format(
            "<html>ID: %d | By: %s | Date: %s<br>Heading: %s<br>%s</html>",
            n.getNoticeId(), n.getNoticeBy(), n.getNoticeDate(), n.getNoticeHeading(), n.getNotice()
        );
        model.addElement(formattedNotice);
    }
     DefaultListModel<String> model2 = new DefaultListModel<>(); 
    for (NoticeModel n : noticeList) {
        String formattedNotice = String.format(
            "<html> By: %s | Date: %s<br>Heading: %s<br>%s</html>",
           n.getNoticeBy(), n.getNoticeDate(), n.getNoticeHeading(), n.getNotice()
        );
        model2.addElement(formattedNotice);
    }
    listNoticeListAdmin.setModel(model);
    listAnnouncement.setModel(model2);
    }    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLoginMain = new javax.swing.JPanel();
        lblLoginPicture = new javax.swing.JLabel();
        txtFldUsername = new javax.swing.JTextField();
        btnLoginBtn = new javax.swing.JButton();
        passFldLoginPassword = new javax.swing.JPasswordField();
        lblLoginUsername = new javax.swing.JLabel();
        lblLoginPassword = new javax.swing.JLabel();
        btnReturnHome = new javax.swing.JButton();
        lblLoginError = new javax.swing.JLabel();
        imgLoginLog = new javax.swing.JLabel();
        diaBookingDialog = new javax.swing.JDialog();
        txtFldBookingCourtNo = new javax.swing.JTextField();
        txtFldBookingPhone = new javax.swing.JTextField();
        lblBookingName = new javax.swing.JLabel();
        lblBookingPhoneNumber = new javax.swing.JLabel();
        txtFldBookingDate = new javax.swing.JTextField();
        lblBookingCourt = new javax.swing.JLabel();
        lblBookingDate = new javax.swing.JLabel();
        lblBookingTime = new javax.swing.JLabel();
        txtFldBookingTime = new javax.swing.JTextField();
        btnClearRequest = new javax.swing.JButton();
        lblBookingDialogTitle = new javax.swing.JLabel();
        txtFldBookingName = new javax.swing.JTextField();
        btnBookRequest = new javax.swing.JButton();
        lblBookingError = new javax.swing.JLabel();
        pnlAdmin = new javax.swing.JPanel();
        pnlAdminNavbar = new javax.swing.JPanel();
        btnAdminDashboard = new javax.swing.JButton();
        btnAdminBooking = new javax.swing.JButton();
        btnAdminNotice = new javax.swing.JButton();
        imgAdminLogo = new javax.swing.JLabel();
        btnLogOut = new javax.swing.JButton();
        pnlAdminCard = new javax.swing.JPanel();
        pnlCardDashboard = new javax.swing.JPanel();
        pnlDashboardNewBooking = new javax.swing.JPanel();
        lblnewbooking = new javax.swing.JLabel();
        lblRequestCounter = new javax.swing.JLabel();
        sclPaneBookingRequest = new javax.swing.JScrollPane();
        tblBookingRequest = new javax.swing.JTable();
        lblBookingRequest = new javax.swing.JLabel();
        lblBookings = new javax.swing.JLabel();
        lblAdminDashBoardTitle = new javax.swing.JLabel();
        btnDeleteRequest = new javax.swing.JButton();
        sclPaneBookingRequest1 = new javax.swing.JScrollPane();
        tblBookings = new javax.swing.JTable();
        pnlCardBooking = new javax.swing.JPanel();
        txtFldName = new javax.swing.JTextField();
        txtFldDate = new javax.swing.JTextField();
        txtFldCourt = new javax.swing.JTextField();
        txtFldTime = new javax.swing.JTextField();
        txtFldCustomerId = new javax.swing.JTextField();
        txtFldPhone = new javax.swing.JTextField();
        lblNewBooking = new javax.swing.JLabel();
        btnNewBooking = new javax.swing.JButton();
        btnUpdateBooking = new javax.swing.JButton();
        btnDeleteBooking = new javax.swing.JButton();
        lblCustomerName = new javax.swing.JLabel();
        lblCustomerPhone = new javax.swing.JLabel();
        lblCourt = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblCustomerName1 = new javax.swing.JLabel();
        sclPaneBookingRequest2 = new javax.swing.JScrollPane();
        tblBookingCardRequest = new javax.swing.JTable();
        lblBookingRequestTitleAdmin = new javax.swing.JLabel();
        lblAdminBookingError = new javax.swing.JLabel();
        pnlCardNotice = new javax.swing.JPanel();
        btnAddNotice = new javax.swing.JButton();
        txtFldNoticeNo = new javax.swing.JTextField();
        txtFldNoticeBy = new javax.swing.JTextField();
        txtFldNoticeDate = new javax.swing.JTextField();
        txtFldNoticeHeading = new javax.swing.JTextField();
        sclPaneNotice = new javax.swing.JScrollPane();
        txtAreaNotice = new javax.swing.JTextArea();
        btnUpdateNotice = new javax.swing.JButton();
        btnDeleteNotice = new javax.swing.JButton();
        lblNoticeTitle = new javax.swing.JLabel();
        lblNoticeId = new javax.swing.JLabel();
        lblNoticeBy = new javax.swing.JLabel();
        lblNoticeDate = new javax.swing.JLabel();
        lblNoticeHeading = new javax.swing.JLabel();
        lblNotice = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listNoticeListAdmin = new javax.swing.JList<>();
        lblNoticeError = new javax.swing.JLabel();
        pnlServicesSection = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        imgCourtImage = new javax.swing.JLabel();
        pnlMainScreen = new javax.swing.JPanel();
        sclPaneCustomerPage = new javax.swing.JScrollPane();
        pnlMain = new javax.swing.JPanel();
        pnlMainNavbar = new javax.swing.JPanel();
        lblNavbarHomelbl = new javax.swing.JLabel();
        lblNavbarBooking = new javax.swing.JLabel();
        lblNavbarAnnouncement = new javax.swing.JLabel();
        btnLoginMain = new javax.swing.JButton();
        imgLogo = new javax.swing.JLabel();
        pnlMainCard = new javax.swing.JPanel();
        pnlHomePage = new javax.swing.JPanel();
        pnlMainHero = new javax.swing.JPanel();
        txtAreaHeroTitle = new javax.swing.JTextArea();
        txtAreaHeroTitle2 = new javax.swing.JTextArea();
        txtAreaHeroDesc = new javax.swing.JTextArea();
        btnHeroBtn = new javax.swing.JButton();
        imgHeroImg = new javax.swing.JLabel();
        pnlBookingPage = new javax.swing.JPanel();
        pnlBookingMain = new javax.swing.JPanel();
        imgBookingCourtImage1 = new javax.swing.JLabel();
        txtAreaHeroDesc1 = new javax.swing.JTextArea();
        btnBookingButton = new javax.swing.JButton();
        lblBookingTitle = new javax.swing.JLabel();
        pnlAnnouncementPage = new javax.swing.JPanel();
        pnlAnnouncement = new javax.swing.JPanel();
        cmbBoxAnnouncementSort = new javax.swing.JComboBox<>();
        lblAnnouncementSort = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listAnnouncement = new javax.swing.JList<>();
        lblAnnouncementTitle = new javax.swing.JLabel();

        pnlLoginMain.setBackground(new java.awt.Color(0, 0, 0));
        pnlLoginMain.setForeground(new java.awt.Color(255, 255, 255));
        pnlLoginMain.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlLoginMain.setLayout(null);

        lblLoginPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/views/no backgroundsmall.png"))); // NOI18N
        lblLoginPicture.setText("jLabel1");
        pnlLoginMain.add(lblLoginPicture);
        lblLoginPicture.setBounds(10, 80, 691, 666);

        txtFldUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldUsernameActionPerformed(evt);
            }
        });
        pnlLoginMain.add(txtFldUsername);
        txtFldUsername.setBounds(950, 230, 170, 44);

        btnLoginBtn.setBackground(new java.awt.Color(36, 74, 138));
        btnLoginBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLoginBtn.setForeground(new java.awt.Color(255, 255, 255));
        btnLoginBtn.setText("Login");
        btnLoginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginBtnActionPerformed(evt);
            }
        });
        pnlLoginMain.add(btnLoginBtn);
        btnLoginBtn.setBounds(951, 492, 178, 41);

        passFldLoginPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passFldLoginPasswordActionPerformed(evt);
            }
        });
        pnlLoginMain.add(passFldLoginPassword);
        passFldLoginPassword.setBounds(950, 370, 178, 40);

        lblLoginUsername.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLoginUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblLoginUsername.setText("Username");
        pnlLoginMain.add(lblLoginUsername);
        lblLoginUsername.setBounds(960, 170, 95, 30);

        lblLoginPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLoginPassword.setForeground(new java.awt.Color(255, 255, 255));
        lblLoginPassword.setText("Password");
        pnlLoginMain.add(lblLoginPassword);
        lblLoginPassword.setBounds(960, 320, 95, 30);

        btnReturnHome.setBackground(new java.awt.Color(36, 74, 138));
        btnReturnHome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnReturnHome.setForeground(new java.awt.Color(255, 255, 255));
        btnReturnHome.setText("Return To Home Page");
        btnReturnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnHomeActionPerformed(evt);
            }
        });
        pnlLoginMain.add(btnReturnHome);
        btnReturnHome.setBounds(951, 562, 178, 41);

        lblLoginError.setForeground(new java.awt.Color(255, 0, 51));
        lblLoginError.setFocusable(false);
        lblLoginError.setPreferredSize(new java.awt.Dimension(20, 100));
        pnlLoginMain.add(lblLoginError);
        lblLoginError.setBounds(950, 430, 290, 30);

        imgLoginLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/views/images/150logo.png"))); // NOI18N
        pnlLoginMain.add(imgLoginLog);
        imgLoginLog.setBounds(90, 30, 150, 150);

        diaBookingDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        diaBookingDialog.setBackground(new java.awt.Color(227, 228, 229));
        diaBookingDialog.getContentPane().setLayout(null);

        txtFldBookingCourtNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldBookingCourtNoActionPerformed(evt);
            }
        });
        diaBookingDialog.getContentPane().add(txtFldBookingCourtNo);
        txtFldBookingCourtNo.setBounds(390, 110, 118, 36);
        diaBookingDialog.getContentPane().add(txtFldBookingPhone);
        txtFldBookingPhone.setBounds(240, 110, 118, 36);

        lblBookingName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBookingName.setText("Name");
        diaBookingDialog.getContentPane().add(lblBookingName);
        lblBookingName.setBounds(70, 80, 60, 16);

        lblBookingPhoneNumber.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBookingPhoneNumber.setText("Phone Number");
        diaBookingDialog.getContentPane().add(lblBookingPhoneNumber);
        lblBookingPhoneNumber.setBounds(230, 80, 118, 16);
        diaBookingDialog.getContentPane().add(txtFldBookingDate);
        txtFldBookingDate.setBounds(390, 190, 118, 36);

        lblBookingCourt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBookingCourt.setText("Court Number");
        diaBookingDialog.getContentPane().add(lblBookingCourt);
        lblBookingCourt.setBounds(380, 80, 81, 16);

        lblBookingDate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBookingDate.setText("Date");
        diaBookingDialog.getContentPane().add(lblBookingDate);
        lblBookingDate.setBounds(380, 170, 45, 16);

        lblBookingTime.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBookingTime.setText("Time");
        diaBookingDialog.getContentPane().add(lblBookingTime);
        lblBookingTime.setBounds(70, 160, 45, 16);
        diaBookingDialog.getContentPane().add(txtFldBookingTime);
        txtFldBookingTime.setBounds(80, 190, 118, 36);

        btnClearRequest.setBackground(new java.awt.Color(247, 151, 141));
        btnClearRequest.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClearRequest.setForeground(new java.awt.Color(255, 255, 255));
        btnClearRequest.setText("Clear");
        btnClearRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearRequestActionPerformed(evt);
            }
        });
        diaBookingDialog.getContentPane().add(btnClearRequest);
        btnClearRequest.setBounds(320, 330, 140, 40);

        lblBookingDialogTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblBookingDialogTitle.setText("Booking Info");
        diaBookingDialog.getContentPane().add(lblBookingDialogTitle);
        lblBookingDialogTitle.setBounds(230, 20, 120, 50);

        txtFldBookingName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldBookingNameActionPerformed(evt);
            }
        });
        diaBookingDialog.getContentPane().add(txtFldBookingName);
        txtFldBookingName.setBounds(80, 110, 118, 36);

        btnBookRequest.setBackground(new java.awt.Color(36, 74, 138));
        btnBookRequest.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBookRequest.setForeground(new java.awt.Color(255, 255, 255));
        btnBookRequest.setText("Book");
        btnBookRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookRequestActionPerformed(evt);
            }
        });
        diaBookingDialog.getContentPane().add(btnBookRequest);
        btnBookRequest.setBounds(110, 330, 140, 40);

        lblBookingError.setForeground(new java.awt.Color(204, 0, 0));
        lblBookingError.setPreferredSize(new java.awt.Dimension(80, 20));
        diaBookingDialog.getContentPane().add(lblBookingError);
        lblBookingError.setBounds(90, 240, 400, 20);

        pnlAdmin.setPreferredSize(new java.awt.Dimension(1400, 800));

        pnlAdminNavbar.setBackground(new java.awt.Color(36, 74, 138));
        pnlAdminNavbar.setPreferredSize(new java.awt.Dimension(200, 800));
        pnlAdminNavbar.setLayout(null);

        btnAdminDashboard.setBackground(new java.awt.Color(197, 89, 6));
        btnAdminDashboard.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdminDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnAdminDashboard.setText("Dashboard");
        btnAdminDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminDashboardActionPerformed(evt);
            }
        });
        pnlAdminNavbar.add(btnAdminDashboard);
        btnAdminDashboard.setBounds(15, 280, 150, 34);

        btnAdminBooking.setBackground(new java.awt.Color(197, 89, 6));
        btnAdminBooking.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdminBooking.setForeground(new java.awt.Color(255, 255, 255));
        btnAdminBooking.setText("Manage Booking");
        btnAdminBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminBookingActionPerformed(evt);
            }
        });
        pnlAdminNavbar.add(btnAdminBooking);
        btnAdminBooking.setBounds(15, 370, 150, 34);

        btnAdminNotice.setBackground(new java.awt.Color(197, 89, 6));
        btnAdminNotice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdminNotice.setForeground(new java.awt.Color(255, 255, 255));
        btnAdminNotice.setText("Notice");
        btnAdminNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminNoticeActionPerformed(evt);
            }
        });
        pnlAdminNavbar.add(btnAdminNotice);
        btnAdminNotice.setBounds(15, 470, 150, 34);

        imgAdminLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/views/images/150logo.png"))); // NOI18N
        pnlAdminNavbar.add(imgAdminLogo);
        imgAdminLogo.setBounds(30, 20, 150, 130);

        btnLogOut.setBackground(new java.awt.Color(197, 89, 6));
        btnLogOut.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogOut.setForeground(new java.awt.Color(255, 255, 255));
        btnLogOut.setText("Log Out");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });
        pnlAdminNavbar.add(btnLogOut);
        btnLogOut.setBounds(40, 680, 125, 34);

        pnlAdminCard.setPreferredSize(new java.awt.Dimension(1200, 800));
        pnlAdminCard.setLayout(new java.awt.CardLayout());

        pnlCardDashboard.setBackground(new java.awt.Color(33, 33, 33));
        pnlCardDashboard.setLayout(null);

        lblnewbooking.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblnewbooking.setText("New Booking");

        lblRequestCounter.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblRequestCounter.setPreferredSize(new java.awt.Dimension(40, 40));

        javax.swing.GroupLayout pnlDashboardNewBookingLayout = new javax.swing.GroupLayout(pnlDashboardNewBooking);
        pnlDashboardNewBooking.setLayout(pnlDashboardNewBookingLayout);
        pnlDashboardNewBookingLayout.setHorizontalGroup(
            pnlDashboardNewBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDashboardNewBookingLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlDashboardNewBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRequestCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblnewbooking, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(137, Short.MAX_VALUE))
        );
        pnlDashboardNewBookingLayout.setVerticalGroup(
            pnlDashboardNewBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDashboardNewBookingLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblnewbooking)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblRequestCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pnlCardDashboard.add(pnlDashboardNewBooking);
        pnlDashboardNewBooking.setBounds(445, 137, 0, 0);

        tblBookingRequest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer Id", "Customer Name", "Phone Number", "Court No.", "Date", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBookingRequest.setToolTipText("");
        tblBookingRequest.getTableHeader().setReorderingAllowed(false);
        sclPaneBookingRequest.setViewportView(tblBookingRequest);

        pnlCardDashboard.add(sclPaneBookingRequest);
        sclPaneBookingRequest.setBounds(30, 320, 547, 270);

        lblBookingRequest.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblBookingRequest.setForeground(new java.awt.Color(255, 255, 255));
        lblBookingRequest.setText("Booking Requests");
        pnlCardDashboard.add(lblBookingRequest);
        lblBookingRequest.setBounds(30, 270, 164, 46);

        lblBookings.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblBookings.setForeground(new java.awt.Color(255, 255, 255));
        lblBookings.setText("Bookings");
        pnlCardDashboard.add(lblBookings);
        lblBookings.setBounds(610, 270, 164, 46);

        lblAdminDashBoardTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblAdminDashBoardTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblAdminDashBoardTitle.setText("Admin Dashboard");
        pnlCardDashboard.add(lblAdminDashBoardTitle);
        lblAdminDashBoardTitle.setBounds(512, 49, 210, 36);

        btnDeleteRequest.setBackground(new java.awt.Color(255, 151, 141));
        btnDeleteRequest.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeleteRequest.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteRequest.setText("Delete Request");
        btnDeleteRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteRequestActionPerformed(evt);
            }
        });
        pnlCardDashboard.add(btnDeleteRequest);
        btnDeleteRequest.setBounds(256, 645, 149, 50);

        tblBookings.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer Id", "Customer Name", "Phone Number", "Court No.", "Date", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBookings.setToolTipText("");
        tblBookings.getTableHeader().setReorderingAllowed(false);
        sclPaneBookingRequest1.setViewportView(tblBookings);

        pnlCardDashboard.add(sclPaneBookingRequest1);
        sclPaneBookingRequest1.setBounds(610, 320, 567, 336);

        pnlAdminCard.add(pnlCardDashboard, "card2");

        pnlCardBooking.setBackground(new java.awt.Color(33, 33, 33));
        pnlCardBooking.setLayout(null);

        txtFldName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldNameActionPerformed(evt);
            }
        });
        pnlCardBooking.add(txtFldName);
        txtFldName.setBounds(540, 170, 126, 31);

        txtFldDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldDateActionPerformed(evt);
            }
        });
        pnlCardBooking.add(txtFldDate);
        txtFldDate.setBounds(540, 270, 126, 31);

        txtFldCourt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldCourtActionPerformed(evt);
            }
        });
        pnlCardBooking.add(txtFldCourt);
        txtFldCourt.setBounds(290, 270, 126, 31);

        txtFldTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldTimeActionPerformed(evt);
            }
        });
        pnlCardBooking.add(txtFldTime);
        txtFldTime.setBounds(810, 270, 126, 31);

        txtFldCustomerId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldCustomerIdActionPerformed(evt);
            }
        });
        pnlCardBooking.add(txtFldCustomerId);
        txtFldCustomerId.setBounds(290, 170, 126, 31);

        txtFldPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldPhoneActionPerformed(evt);
            }
        });
        pnlCardBooking.add(txtFldPhone);
        txtFldPhone.setBounds(810, 170, 126, 31);

        lblNewBooking.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblNewBooking.setForeground(new java.awt.Color(255, 255, 255));
        lblNewBooking.setText("Manage Booking");
        pnlCardBooking.add(lblNewBooking);
        lblNewBooking.setBounds(500, 80, 200, 34);

        btnNewBooking.setBackground(new java.awt.Color(36, 74, 138));
        btnNewBooking.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewBooking.setForeground(new java.awt.Color(255, 255, 255));
        btnNewBooking.setText("Add Booking");
        btnNewBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewBookingActionPerformed(evt);
            }
        });
        pnlCardBooking.add(btnNewBooking);
        btnNewBooking.setBounds(340, 670, 126, 42);

        btnUpdateBooking.setBackground(new java.awt.Color(36, 74, 138));
        btnUpdateBooking.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateBooking.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateBooking.setText("Update Booking");
        btnUpdateBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateBookingActionPerformed(evt);
            }
        });
        pnlCardBooking.add(btnUpdateBooking);
        btnUpdateBooking.setBounds(570, 670, 126, 42);

        btnDeleteBooking.setBackground(new java.awt.Color(36, 74, 138));
        btnDeleteBooking.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeleteBooking.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteBooking.setText("Delete Booking");
        btnDeleteBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteBookingActionPerformed(evt);
            }
        });
        pnlCardBooking.add(btnDeleteBooking);
        btnDeleteBooking.setBounds(810, 670, 126, 42);

        lblCustomerName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCustomerName.setForeground(new java.awt.Color(255, 255, 255));
        lblCustomerName.setText("Customer Name");
        pnlCardBooking.add(lblCustomerName);
        lblCustomerName.setBounds(530, 140, 117, 16);

        lblCustomerPhone.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCustomerPhone.setForeground(new java.awt.Color(255, 255, 255));
        lblCustomerPhone.setText("Phone");
        pnlCardBooking.add(lblCustomerPhone);
        lblCustomerPhone.setBounds(800, 140, 117, 16);

        lblCourt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCourt.setForeground(new java.awt.Color(255, 255, 255));
        lblCourt.setText("Court");
        pnlCardBooking.add(lblCourt);
        lblCourt.setBounds(280, 240, 117, 16);

        lblDate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("Date");
        pnlCardBooking.add(lblDate);
        lblDate.setBounds(530, 240, 117, 16);

        lblTime.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setText("Time");
        pnlCardBooking.add(lblTime);
        lblTime.setBounds(800, 230, 117, 16);

        lblCustomerName1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCustomerName1.setForeground(new java.awt.Color(255, 255, 255));
        lblCustomerName1.setText("Customer Id");
        pnlCardBooking.add(lblCustomerName1);
        lblCustomerName1.setBounds(280, 130, 117, 16);

        tblBookingCardRequest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer Id", "Customer Name", "Phone Number", "Court No.", "Date", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBookingCardRequest.setToolTipText("");
        tblBookingCardRequest.getTableHeader().setReorderingAllowed(false);
        sclPaneBookingRequest2.setViewportView(tblBookingCardRequest);

        pnlCardBooking.add(sclPaneBookingRequest2);
        sclPaneBookingRequest2.setBounds(400, 410, 460, 240);

        lblBookingRequestTitleAdmin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblBookingRequestTitleAdmin.setForeground(new java.awt.Color(255, 255, 255));
        lblBookingRequestTitleAdmin.setText("Booking Request Table");
        pnlCardBooking.add(lblBookingRequestTitleAdmin);
        lblBookingRequestTitleAdmin.setBounds(370, 360, 260, 40);

        lblAdminBookingError.setForeground(new java.awt.Color(255, 0, 0));
        lblAdminBookingError.setPreferredSize(new java.awt.Dimension(80, 20));
        pnlCardBooking.add(lblAdminBookingError);
        lblAdminBookingError.setBounds(370, 330, 220, 30);

        pnlAdminCard.add(pnlCardBooking, "card3");

        pnlCardNotice.setBackground(new java.awt.Color(33, 33, 33));
        pnlCardNotice.setLayout(null);

        btnAddNotice.setBackground(new java.awt.Color(36, 74, 138));
        btnAddNotice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddNotice.setForeground(new java.awt.Color(255, 255, 255));
        btnAddNotice.setText("Add Notice");
        btnAddNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNoticeActionPerformed(evt);
            }
        });
        pnlCardNotice.add(btnAddNotice);
        btnAddNotice.setBounds(434, 720, 125, 35);
        pnlCardNotice.add(txtFldNoticeNo);
        txtFldNoticeNo.setBounds(330, 180, 170, 40);
        pnlCardNotice.add(txtFldNoticeBy);
        txtFldNoticeBy.setBounds(860, 170, 210, 40);
        pnlCardNotice.add(txtFldNoticeDate);
        txtFldNoticeDate.setBounds(330, 280, 170, 40);
        pnlCardNotice.add(txtFldNoticeHeading);
        txtFldNoticeHeading.setBounds(860, 280, 200, 33);

        txtAreaNotice.setColumns(20);
        txtAreaNotice.setRows(5);
        txtAreaNotice.setBorder(null);
        sclPaneNotice.setViewportView(txtAreaNotice);

        pnlCardNotice.add(sclPaneNotice);
        sclPaneNotice.setBounds(530, 220, 230, 100);

        btnUpdateNotice.setBackground(new java.awt.Color(36, 74, 138));
        btnUpdateNotice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateNotice.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateNotice.setText("Update Notice");
        btnUpdateNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateNoticeActionPerformed(evt);
            }
        });
        pnlCardNotice.add(btnUpdateNotice);
        btnUpdateNotice.setBounds(632, 720, 125, 35);

        btnDeleteNotice.setBackground(new java.awt.Color(36, 74, 138));
        btnDeleteNotice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeleteNotice.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteNotice.setText("Delete Notice");
        btnDeleteNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteNoticeActionPerformed(evt);
            }
        });
        pnlCardNotice.add(btnDeleteNotice);
        btnDeleteNotice.setBounds(825, 720, 125, 35);

        lblNoticeTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblNoticeTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblNoticeTitle.setText("Notice");
        pnlCardNotice.add(lblNoticeTitle);
        lblNoticeTitle.setBounds(650, 50, 90, 67);

        lblNoticeId.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoticeId.setForeground(new java.awt.Color(255, 255, 255));
        lblNoticeId.setText("Notice Id");
        pnlCardNotice.add(lblNoticeId);
        lblNoticeId.setBounds(330, 150, 117, 16);

        lblNoticeBy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoticeBy.setForeground(new java.awt.Color(255, 255, 255));
        lblNoticeBy.setText("Notice By");
        pnlCardNotice.add(lblNoticeBy);
        lblNoticeBy.setBounds(860, 140, 117, 16);

        lblNoticeDate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoticeDate.setForeground(new java.awt.Color(255, 255, 255));
        lblNoticeDate.setText("Notice Date");
        pnlCardNotice.add(lblNoticeDate);
        lblNoticeDate.setBounds(330, 250, 117, 16);

        lblNoticeHeading.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoticeHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblNoticeHeading.setText("Notice Heading");
        pnlCardNotice.add(lblNoticeHeading);
        lblNoticeHeading.setBounds(860, 250, 117, 16);

        lblNotice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNotice.setForeground(new java.awt.Color(255, 255, 255));
        lblNotice.setText("Notice");
        pnlCardNotice.add(lblNotice);
        lblNotice.setBounds(540, 190, 117, 16);

        listNoticeListAdmin.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listNoticeListAdmin);

        pnlCardNotice.add(jScrollPane1);
        jScrollPane1.setBounds(370, 420, 650, 240);

        lblNoticeError.setForeground(new java.awt.Color(255, 0, 0));
        lblNoticeError.setPreferredSize(new java.awt.Dimension(80, 20));
        pnlCardNotice.add(lblNoticeError);
        lblNoticeError.setBounds(380, 350, 290, 30);

        pnlAdminCard.add(pnlCardNotice, "card5");

        javax.swing.GroupLayout pnlAdminLayout = new javax.swing.GroupLayout(pnlAdmin);
        pnlAdmin.setLayout(pnlAdminLayout);
        pnlAdminLayout.setHorizontalGroup(
            pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdminLayout.createSequentialGroup()
                .addComponent(pnlAdminNavbar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlAdminCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlAdminLayout.setVerticalGroup(
            pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdminLayout.createSequentialGroup()
                .addGroup(pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAdminNavbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlAdminCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Court Rentals");

        imgCourtImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/views/images/250court.jpg"))); // NOI18N

        javax.swing.GroupLayout pnlServicesSectionLayout = new javax.swing.GroupLayout(pnlServicesSection);
        pnlServicesSection.setLayout(pnlServicesSectionLayout);
        pnlServicesSectionLayout.setHorizontalGroup(
            pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                .addGroup(pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(imgCourtImage)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlServicesSectionLayout.setVerticalGroup(
            pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(imgCourtImage)
                .addContainerGap(201, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1400, 800));
        setResizable(false);
        setSize(new java.awt.Dimension(1400, 800));

        pnlMainScreen.setBackground(new java.awt.Color(245, 246, 250));
        pnlMainScreen.setPreferredSize(new java.awt.Dimension(1400, 800));

        sclPaneCustomerPage.setBackground(new java.awt.Color(153, 153, 153));
        sclPaneCustomerPage.setBorder(null);
        sclPaneCustomerPage.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sclPaneCustomerPage.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sclPaneCustomerPage.setPreferredSize(new java.awt.Dimension(1400, 800));
        sclPaneCustomerPage.setViewportView(pnlMain);

        pnlMain.setBackground(new java.awt.Color(227, 228, 229));
        pnlMain.setLayout(null);

        pnlMainNavbar.setBackground(new java.awt.Color(255, 255, 255));
        pnlMainNavbar.setForeground(new java.awt.Color(255, 255, 255));
        pnlMainNavbar.setPreferredSize(new java.awt.Dimension(1400, 66));
        pnlMainNavbar.setLayout(null);

        lblNavbarHomelbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNavbarHomelbl.setForeground(new java.awt.Color(102, 102, 102));
        lblNavbarHomelbl.setText("Home");
        lblNavbarHomelbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNavbarHomelblMouseClicked(evt);
            }
        });
        pnlMainNavbar.add(lblNavbarHomelbl);
        lblNavbarHomelbl.setBounds(632, 22, 42, 24);

        lblNavbarBooking.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNavbarBooking.setForeground(new java.awt.Color(102, 102, 102));
        lblNavbarBooking.setText("Booking");
        lblNavbarBooking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNavbarBookingMouseClicked(evt);
            }
        });
        pnlMainNavbar.add(lblNavbarBooking);
        lblNavbarBooking.setBounds(755, 22, 79, 24);

        lblNavbarAnnouncement.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNavbarAnnouncement.setForeground(new java.awt.Color(102, 102, 102));
        lblNavbarAnnouncement.setText("Announcement");
        lblNavbarAnnouncement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNavbarAnnouncementMouseClicked(evt);
            }
        });
        pnlMainNavbar.add(lblNavbarAnnouncement);
        lblNavbarAnnouncement.setBounds(889, 22, 128, 24);

        btnLoginMain.setBackground(new java.awt.Color(36, 74, 138));
        btnLoginMain.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLoginMain.setForeground(new java.awt.Color(255, 255, 255));
        btnLoginMain.setText("LOGIN");
        btnLoginMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginMainActionPerformed(evt);
            }
        });
        pnlMainNavbar.add(btnLoginMain);
        btnLoginMain.setBounds(1040, 10, 92, 45);

        pnlMain.add(pnlMainNavbar);
        pnlMainNavbar.setBounds(150, 30, 1170, 66);

        imgLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/views/images/HoopsHeaven.png"))); // NOI18N
        pnlMain.add(imgLogo);
        imgLogo.setBounds(40, 10, 100, 101);

        pnlMainCard.setBackground(new java.awt.Color(245, 246, 250));
        pnlMainCard.setPreferredSize(new java.awt.Dimension(1400, 680));
        pnlMainCard.setLayout(new java.awt.CardLayout());

        pnlHomePage.setBackground(new java.awt.Color(227, 228, 229));
        pnlHomePage.setPreferredSize(new java.awt.Dimension(1400, 1100));

        pnlMainHero.setBackground(new java.awt.Color(255, 255, 255));
        pnlMainHero.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtAreaHeroTitle.setColumns(20);
        txtAreaHeroTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txtAreaHeroTitle.setRows(5);
        txtAreaHeroTitle.setText("It's Time to\nHOOP!");
        txtAreaHeroTitle.setBorder(null);
        txtAreaHeroTitle.setFocusable(false);
        txtAreaHeroTitle.setOpaque(false);

        txtAreaHeroTitle2.setColumns(20);
        txtAreaHeroTitle2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtAreaHeroTitle2.setRows(5);
        txtAreaHeroTitle2.setText("Experience the thrill of basketball at state of art basketball court at \nHOOPS HEAVEN.\n");
        txtAreaHeroTitle2.setBorder(null);
        txtAreaHeroTitle2.setFocusable(false);
        txtAreaHeroTitle2.setOpaque(false);

        txtAreaHeroDesc.setColumns(20);
        txtAreaHeroDesc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtAreaHeroDesc.setRows(5);
        txtAreaHeroDesc.setText("Whether it’s a one-on-one or a full-court battle, our facility offers the perfect environment \nfor basketball lovers to play, train, and thrive.");
        txtAreaHeroDesc.setBorder(null);
        txtAreaHeroDesc.setFocusable(false);
        txtAreaHeroDesc.setOpaque(false);

        btnHeroBtn.setBackground(new java.awt.Color(36, 74, 138));
        btnHeroBtn.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnHeroBtn.setForeground(new java.awt.Color(255, 255, 255));
        btnHeroBtn.setText("BOOK NOW");
        btnHeroBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHeroBtnActionPerformed(evt);
            }
        });

        imgHeroImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/views/images/500heroimage.jpg"))); // NOI18N

        javax.swing.GroupLayout pnlMainHeroLayout = new javax.swing.GroupLayout(pnlMainHero);
        pnlMainHero.setLayout(pnlMainHeroLayout);
        pnlMainHeroLayout.setHorizontalGroup(
            pnlMainHeroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainHeroLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(pnlMainHeroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHeroBtn)
                    .addComponent(txtAreaHeroTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAreaHeroDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAreaHeroTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imgHeroImg)
                .addGap(212, 212, 212))
        );
        pnlMainHeroLayout.setVerticalGroup(
            pnlMainHeroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainHeroLayout.createSequentialGroup()
                .addGroup(pnlMainHeroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainHeroLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(txtAreaHeroTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAreaHeroTitle2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtAreaHeroDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(btnHeroBtn))
                    .addGroup(pnlMainHeroLayout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(imgHeroImg)))
                .addContainerGap(93, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlHomePageLayout = new javax.swing.GroupLayout(pnlHomePage);
        pnlHomePage.setLayout(pnlHomePageLayout);
        pnlHomePageLayout.setHorizontalGroup(
            pnlHomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomePageLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(pnlMainHero, javax.swing.GroupLayout.PREFERRED_SIZE, 1235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(382, Short.MAX_VALUE))
        );
        pnlHomePageLayout.setVerticalGroup(
            pnlHomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomePageLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(pnlMainHero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        pnlMainCard.add(pnlHomePage, "card2");

        pnlBookingPage.setBackground(new java.awt.Color(227, 228, 229));
        pnlBookingPage.setPreferredSize(new java.awt.Dimension(1400, 680));
        pnlBookingPage.setLayout(null);

        pnlBookingMain.setBackground(new java.awt.Color(255, 255, 255));
        pnlBookingMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        pnlBookingMain.setLayout(null);

        imgBookingCourtImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/views/images/500court.jpg"))); // NOI18N
        pnlBookingMain.add(imgBookingCourtImage1);
        imgBookingCourtImage1.setBounds(130, 130, 500, 333);

        txtAreaHeroDesc1.setColumns(20);
        txtAreaHeroDesc1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtAreaHeroDesc1.setRows(5);
        txtAreaHeroDesc1.setText("Book your court effortlessly! Whether it's a friendly match or an intense practice session,\nour well-maintained courts are available for hourly or daily rentals.");
        txtAreaHeroDesc1.setBorder(null);
        txtAreaHeroDesc1.setFocusable(false);
        txtAreaHeroDesc1.setOpaque(false);
        pnlBookingMain.add(txtAreaHeroDesc1);
        txtAreaHeroDesc1.setBounds(140, 480, 495, 40);

        btnBookingButton.setBackground(new java.awt.Color(36, 74, 138));
        btnBookingButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBookingButton.setForeground(new java.awt.Color(255, 255, 255));
        btnBookingButton.setText("Book Court");
        btnBookingButton.setBorder(null);
        btnBookingButton.setBorderPainted(false);
        btnBookingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookingButtonActionPerformed(evt);
            }
        });
        pnlBookingMain.add(btnBookingButton);
        btnBookingButton.setBounds(280, 540, 180, 50);

        lblBookingTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblBookingTitle.setText("Booking");
        pnlBookingMain.add(lblBookingTitle);
        lblBookingTitle.setBounds(340, 40, 100, 40);

        pnlBookingPage.add(pnlBookingMain);
        pnlBookingMain.setBounds(290, 0, 760, 630);

        pnlMainCard.add(pnlBookingPage, "card3");

        pnlAnnouncementPage.setBackground(new java.awt.Color(227, 228, 229));
        pnlAnnouncementPage.setPreferredSize(new java.awt.Dimension(1400, 680));
        pnlAnnouncementPage.setLayout(null);

        pnlAnnouncement.setBackground(new java.awt.Color(255, 255, 255));
        pnlAnnouncement.setPreferredSize(new java.awt.Dimension(1220, 510));
        pnlAnnouncement.setLayout(null);

        cmbBoxAnnouncementSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlAnnouncement.add(cmbBoxAnnouncementSort);
        cmbBoxAnnouncementSort.setBounds(610, 170, 120, 30);

        lblAnnouncementSort.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblAnnouncementSort.setText("Sort By");
        pnlAnnouncement.add(lblAnnouncementSort);
        lblAnnouncementSort.setBounds(620, 120, 70, 40);

        listAnnouncement.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(listAnnouncement);

        pnlAnnouncement.add(jScrollPane3);
        jScrollPane3.setBounds(80, 170, 520, 290);

        lblAnnouncementTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblAnnouncementTitle.setText("Announcement");
        pnlAnnouncement.add(lblAnnouncementTitle);
        lblAnnouncementTitle.setBounds(280, 30, 190, 40);

        pnlAnnouncementPage.add(pnlAnnouncement);
        pnlAnnouncement.setBounds(320, 50, 770, 510);

        pnlMainCard.add(pnlAnnouncementPage, "card4");

        pnlMain.add(pnlMainCard);
        pnlMainCard.setBounds(0, 120, 1690, 680);

        sclPaneCustomerPage.setViewportView(pnlMain);

        javax.swing.GroupLayout pnlMainScreenLayout = new javax.swing.GroupLayout(pnlMainScreen);
        pnlMainScreen.setLayout(pnlMainScreenLayout);
        pnlMainScreenLayout.setHorizontalGroup(
            pnlMainScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sclPaneCustomerPage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlMainScreenLayout.setVerticalGroup(
            pnlMainScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sclPaneCustomerPage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1400, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlMainScreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlMainScreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    //Method to set up switching between different panels using card layout
    
    private void initializeLayout(){
        cardLayout = new java.awt.CardLayout();
        getContentPane().setLayout(cardLayout);
        
        getContentPane().add(pnlMainScreen,"MainScreen");
        getContentPane().add(pnlLoginMain,"LoginScreen");
        getContentPane().add(pnlAdmin,"AdminScreen");
        
        loadScreen("MainScreen");
        
    }
    private void loadScreen(String screen){
        cardLayout.show(getContentPane(),screen);
    }
    //Method to initialize data
    
    private void initializeData(){
        requestQueue = new CustomQueue(14);
        bookingList = new ArrayList();
        noticeList = new ArrayList();
        
        
        addRequest(new CustomerModel("Bibhusan","9803639288","10:00am","2024-12-28",(short) 1));
        addRequest(new CustomerModel("Nirajan","9841287660","11:00am","2024-12-28",(short) 1));
        
        addBooking(new CustomerModel(1,"Bibhusan","9803639288","10:00am","2024-12-28",(short) 1));
        addBooking(new CustomerModel(2,"Pawan","9746478675","10:00am","2024-12-28",(short) 1));
        
        addNotice(new NoticeModel(1,"Bibhusan Silwal","2024-12-26","Three Point Contest Announcement","We are going to organize a three point contest with prizepool of 1 lakhs Rupees."));
    }
    
    private void txtFldUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldUsernameActionPerformed

    private void btnLoginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginBtnActionPerformed
        // TODO add your handling code here:
        String username = txtFldUsername.getText();
        String password = new String(passFldLoginPassword.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            lblLoginError.setText("Please enter your username and password.");
        } 
        else if (!username.equals("admin") || !password.equals("admin")) {
            lblLoginError.setText("Username and password mismatch.");
        } 
        else {
            lblLoginError.setText(""); 
            txtFldUsername.setText("");
            passFldLoginPassword.setText("");
            loadScreen("AdminScreen"); 
        }
        
    }//GEN-LAST:event_btnLoginBtnActionPerformed

    private void passFldLoginPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passFldLoginPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passFldLoginPasswordActionPerformed

    private void btnAdminNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminNoticeActionPerformed
        // TODO add your handling code here:
        pnlAdminCard.removeAll();
        pnlAdminCard.add(pnlCardNotice);
        pnlAdminCard.repaint();
        pnlAdminCard.revalidate();
    }//GEN-LAST:event_btnAdminNoticeActionPerformed

    private void txtFldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldNameActionPerformed

    private void txtFldDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldDateActionPerformed

    private void txtFldCourtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldCourtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldCourtActionPerformed

    private void txtFldTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldTimeActionPerformed

    private void btnAdminDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminDashboardActionPerformed
        // TODO add your handling code here:
        pnlAdminCard.removeAll();
        pnlAdminCard.add(pnlCardDashboard);
        pnlAdminCard.repaint();
        pnlAdminCard.revalidate();
    }//GEN-LAST:event_btnAdminDashboardActionPerformed

    private void btnAdminBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminBookingActionPerformed
        // TODO add your handling code here:
        pnlAdminCard.removeAll();
        pnlAdminCard.add(pnlCardBooking);
        pnlAdminCard.repaint();
        pnlAdminCard.revalidate();
    }//GEN-LAST:event_btnAdminBookingActionPerformed

    private void txtFldCustomerIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldCustomerIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldCustomerIdActionPerformed

    private void txtFldPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldPhoneActionPerformed

    private void btnAddNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNoticeActionPerformed
        // TODO add your handling code here:
     try{
     int noticeId = Integer.parseInt(txtFldNoticeNo.getText());
    String noticeBy = txtFldNoticeBy.getText();
    String noticeDate = txtFldNoticeDate.getText();
    String noticeHeading = txtFldNoticeHeading.getText();
    String notice = txtAreaNotice.getText();
    
    lblNoticeError.setText(""); // Clear previous error message

    if (txtFldNoticeNo.getText().isEmpty() || noticeBy.isEmpty() || noticeDate.isEmpty() || noticeHeading.isEmpty() || notice.isEmpty()) {
        lblNoticeError.setText("Please fill in all fields.");
    } else {
        boolean exists = false;
        for (NoticeModel existingNotice : noticeList) {
            if (existingNotice.getNoticeId() == noticeId) {
                exists = true;
                break;
            }
        }
        
        if (exists) {
            lblNoticeError.setText("Notice with this ID already exists.");
        } else {
            txtFldNoticeNo.setText("");
            txtFldNoticeBy.setText("");
            txtFldNoticeDate.setText("");
            txtFldNoticeHeading.setText("");
            txtAreaNotice.setText("");

            addNotice(new NoticeModel(noticeId, noticeBy, noticeDate, noticeHeading, notice));
        }
    } }
     catch (NumberFormatException ex) {
            lblNoticeError.setText("Invalid Notice ID. Must be a number.");
        }
    }//GEN-LAST:event_btnAddNoticeActionPerformed

    private void btnUpdateNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateNoticeActionPerformed
        // TODO add your handling code here:
    int noticeId = Integer.parseInt(txtFldNoticeNo.getText());
    String noticeBy = txtFldNoticeBy.getText();
    String noticeDate = txtFldNoticeDate.getText();
    String noticeHeading = txtFldNoticeHeading.getText();
    String notice = txtAreaNotice.getText();
    
    lblNoticeError.setText(""); // Clear previous error message

    if (txtFldNoticeNo.getText().isEmpty() || noticeBy.isEmpty() || noticeDate.isEmpty() || noticeHeading.isEmpty() || notice.isEmpty()) {
        lblNoticeError.setText("Please fill in all fields.");
    } else {
        boolean exists =false ;
        for (NoticeModel existingNotice : noticeList) {
            if (existingNotice.getNoticeId() == noticeId) {
                exists = true;
                break;
            }
        }
        
        if (exists) {
             txtFldNoticeNo.setText("");
            txtFldNoticeBy.setText("");
            txtFldNoticeDate.setText("");
            txtFldNoticeHeading.setText("");
            txtAreaNotice.setText("");

            updateNotice(new NoticeModel(noticeId,noticeBy,noticeDate,noticeHeading,notice));
            
        } else {
           lblNoticeError.setText("Notice with this ID does not exist.");
        }
    }  
         
    }//GEN-LAST:event_btnUpdateNoticeActionPerformed
    
    private void btnDeleteNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteNoticeActionPerformed
        // TODO add your handling code here:
        String NoticeIdText = txtFldNoticeNo.getText();
    try{
    if (NoticeIdText.isEmpty()) {
        lblAdminBookingError.setText("Notice ID is required.");
    } 
    else {
        boolean exists =false ;
        int noticeId = Integer.parseInt(txtFldNoticeNo.getText());
        for (NoticeModel existingNotice : noticeList) {
            if (existingNotice.getNoticeId() == noticeId) {
                exists = true;
                break;
            }
        }
        if(exists){
            
            
            lblNoticeError.setText(""); 
            txtFldNoticeNo.setText("");
            deleteNotice(noticeId);
        
        }
        else{
            lblNoticeError.setText("Notice Id doesn't Exists");
        }
        
    }
    }
    catch (NumberFormatException ex) {
            lblNoticeError.setText("Invalid Notice ID. Must be a number.");
        }
    }//GEN-LAST:event_btnDeleteNoticeActionPerformed

    private void lblNavbarBookingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNavbarBookingMouseClicked
        // TODO add your handling code here:
        pnlMainCard.removeAll();
        pnlMainCard.add(pnlBookingPage);
        pnlMainCard.repaint();
        pnlMainCard.revalidate();
    }//GEN-LAST:event_lblNavbarBookingMouseClicked

    private void lblNavbarAnnouncementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNavbarAnnouncementMouseClicked
        // TODO add your handling code here:
        pnlMainCard.removeAll();
        pnlMainCard.add(pnlAnnouncementPage);
        pnlMainCard.repaint();
        pnlMainCard.revalidate();
    }//GEN-LAST:event_lblNavbarAnnouncementMouseClicked

    private void lblNavbarHomelblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNavbarHomelblMouseClicked
        // TODO add your handling code here:
        pnlMainCard.removeAll();
        pnlMainCard.add(pnlHomePage);
        pnlMainCard.repaint();
        pnlMainCard.revalidate();
    }//GEN-LAST:event_lblNavbarHomelblMouseClicked

    private void btnBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookingButtonActionPerformed
        // TODO add your handling code here:
        diaBookingDialog.setVisible(true);
        diaBookingDialog.setLocationRelativeTo(null);
        diaBookingDialog.setSize(600,450);
    }//GEN-LAST:event_btnBookingButtonActionPerformed

    private void txtFldBookingCourtNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldBookingCourtNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldBookingCourtNoActionPerformed

    private void btnClearRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearRequestActionPerformed
        // TODO add your handling code here:
         txtFldBookingName.setText("");
        txtFldBookingPhone.setText("");
        txtFldBookingDate.setText("");
        txtFldBookingTime.setText("");
        txtFldBookingCourtNo.setText("");
        
        
    }//GEN-LAST:event_btnClearRequestActionPerformed

    private void txtFldBookingNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldBookingNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldBookingNameActionPerformed

    private void btnLoginMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginMainActionPerformed
        // TODO add your handling code here:
        loadScreen("LoginScreen");
    }//GEN-LAST:event_btnLoginMainActionPerformed

    private void btnBookRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookRequestActionPerformed
        // TODO add your handling code here:
                                             
    String customerName = txtFldBookingName.getText();
    String customerPhone = txtFldBookingPhone.getText();
    String customerDate = txtFldBookingDate.getText();
    String customerTime = txtFldBookingTime.getText();
    String customerCourtNoText = txtFldBookingCourtNo.getText();
    
    if (customerName.isEmpty()) {
        lblBookingError.setText("Customer Name is required.");
    } 
    else if (customerPhone.isEmpty()) {
        lblBookingError.setText("Customer Phone is required.");
    } 
    else if (customerDate.isEmpty()) {
        lblBookingError.setText("Booking Date is required.");
    } 
    else if (customerTime.isEmpty()) {
        lblBookingError.setText("Booking Time is required.");
    } 
    else if (customerCourtNoText.isEmpty()) {
        lblBookingError.setText("Court Number is required.");
    } 
    else if (!customerPhone.matches("\\d{10}")) {
        lblBookingError.setText("Invalid phone number. Must be 10 digits.");
    } 
    else {
        try {
            short customerCourtNo = Short.parseShort(customerCourtNoText);
            lblBookingError.setText(""); 
            txtFldBookingName.setText("");
            txtFldBookingPhone.setText("");
            txtFldBookingDate.setText("");
            txtFldBookingTime.setText("");
            txtFldBookingCourtNo.setText("");
            addRequest(new CustomerModel(customerName, customerPhone, customerTime, customerDate, customerCourtNo));
        } catch (NumberFormatException ex) {
            lblBookingError.setText("Invalid Court Number. Must be a number.");
        }
    }


    }//GEN-LAST:event_btnBookRequestActionPerformed

    private void btnDeleteRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteRequestActionPerformed
        // TODO add your handling code here:
        deleteRequest();
    }//GEN-LAST:event_btnDeleteRequestActionPerformed

    private void btnNewBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewBookingActionPerformed
        // TODO add your handling code here:
        String customerIdText = txtFldCustomerId.getText();
    String customerName = txtFldName.getText();
    String customerPhone = txtFldPhone.getText();
    String customerDate = txtFldDate.getText();
    String customerTime = txtFldTime.getText();
    String customerCourtNoText = txtFldCourt.getText();
    
    if (customerIdText.isEmpty()) {
        lblAdminBookingError.setText("Customer ID is required.");
    } 
    else if (customerName.isEmpty()) {
        lblAdminBookingError.setText("Customer Name is required.");
    } 
    else if (customerPhone.isEmpty()) {
        lblAdminBookingError.setText("Customer Phone is required.");
    } 
    else if (customerDate.isEmpty()) {
        lblAdminBookingError.setText("Booking Date is required.");
    } 
    else if (customerTime.isEmpty()) {
        lblAdminBookingError.setText("Booking Time is required.");
    } 
    else if (customerCourtNoText.isEmpty()) {
        lblAdminBookingError.setText("Court Number is required.");
    } 
    else if (!customerPhone.matches("\\d{10}")) {
        lblAdminBookingError.setText("Invalid phone number. Must be 10 digits.");
    } 
    else {
        int customerId = Integer.parseInt(customerIdText);    
        boolean exists = false;
        for (CustomerModel existingBooking: bookingList) {
            if (existingBooking.getCustomerId() == customerId) {
                exists = true;
                break;
            }
        }
        
        if (exists) {
            lblAdminBookingError.setText("Booking with this ID already exists.");
        }
        else{
        try {
            
            short customerCourtNo = Short.parseShort(customerCourtNoText);
            lblAdminBookingError.setText(""); 
            txtFldCustomerId.setText("");
            txtFldName.setText("");
            txtFldPhone.setText("");
            txtFldDate.setText("");
            txtFldTime.setText("");
            txtFldCourt.setText("");
            addBooking(new CustomerModel(customerId, customerName, customerPhone, customerTime, customerDate, customerCourtNo));
        } catch (NumberFormatException ex) {
            lblAdminBookingError.setText("Invalid Customer ID or Court Number. Must be a number.");
        }
        }
    }
    }//GEN-LAST:event_btnNewBookingActionPerformed

    private void btnUpdateBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateBookingActionPerformed
        // TODO add your handling code here:
        String customerIdText = txtFldCustomerId.getText();
    String customerName = txtFldName.getText();
    String customerPhone = txtFldPhone.getText();
    String customerDate = txtFldDate.getText();
    String customerTime = txtFldTime.getText();
    String customerCourtNoText = txtFldCourt.getText();
    
    if (customerIdText.isEmpty()) {
        lblAdminBookingError.setText("Customer ID is required.");
    } 
    else if (customerName.isEmpty()) {
        lblAdminBookingError.setText("Customer Name is required.");
    } 
    else if (customerPhone.isEmpty()) {
        lblAdminBookingError.setText("Customer Phone is required.");
    } 
    else if (customerDate.isEmpty()) {
        lblAdminBookingError.setText("Booking Date is required.");
    } 
    else if (customerTime.isEmpty()) {
        lblAdminBookingError.setText("Booking Time is required.");
    } 
    else if (customerCourtNoText.isEmpty()) {
        lblAdminBookingError.setText("Court Number is required.");
    } 
    else if (!customerPhone.matches("\\d{10}")) {
        lblAdminBookingError.setText("Invalid phone number. Must be 10 digits.");
    } 
    else {
        
        int customerId = Integer.parseInt(customerIdText);    
        boolean exists = false;
        for (CustomerModel existingBooking: bookingList) {
            if (existingBooking.getCustomerId() == customerId) {
                exists = true;
                break;
            }
        }
        
        if (!exists) {
            lblAdminBookingError.setText("Booking with this ID does not exist.");
        }
        else{
        try {
            
            short customerCourtNo = Short.parseShort(customerCourtNoText);
            lblAdminBookingError.setText(""); 
            txtFldCustomerId.setText("");
            txtFldName.setText("");
            txtFldPhone.setText("");
            txtFldDate.setText("");
            txtFldTime.setText("");
            txtFldCourt.setText("");
            updateBooking(new CustomerModel(customerId, customerName, customerPhone, customerTime, customerDate, customerCourtNo));
            
        } catch (NumberFormatException ex) {
            lblAdminBookingError.setText("Invalid Customer ID or Court Number. Must be a number.");
        }
        }
    }
    }//GEN-LAST:event_btnUpdateBookingActionPerformed

    private void btnDeleteBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBookingActionPerformed
        // TODO add your handling code here:
         String customerIdText = txtFldCustomerId.getText();
    
    if (customerIdText.isEmpty()) {
        lblAdminBookingError.setText("Customer ID is required.");
    } 
    else {
        int customerId = Integer.parseInt(customerIdText);    
        boolean exists = false;
        for (CustomerModel existingBooking: bookingList) {
            if (existingBooking.getCustomerId() == customerId) {
                exists = true;
                break;
            }
        }
        
        if (!exists) {
            lblAdminBookingError.setText("Booking with this ID does not exist.");
        }
        else{
        try {
            lblAdminBookingError.setText(""); 
            txtFldCustomerId.setText("");
           
            deleteBooking(customerId);
        } catch (NumberFormatException ex) {
            lblAdminBookingError.setText("Invalid Customer ID or Court Number. Must be a number.");
        }
        }
            
    }
    }//GEN-LAST:event_btnDeleteBookingActionPerformed

    private void btnReturnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnHomeActionPerformed
        // TODO add your handling code here:
        loadScreen("MainScreen");
    }//GEN-LAST:event_btnReturnHomeActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // TODO add your handling code here:
        loadScreen("MainScreen");
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void btnHeroBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHeroBtnActionPerformed
        // TODO add your handling code here:
        pnlMainCard.removeAll();
        pnlMainCard.add(pnlBookingPage);
        pnlMainCard.repaint();
        pnlMainCard.revalidate();
    }//GEN-LAST:event_btnHeroBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CourtControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CourtControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CourtControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CourtControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CourtControl().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNotice;
    private javax.swing.JButton btnAdminBooking;
    private javax.swing.JButton btnAdminDashboard;
    private javax.swing.JButton btnAdminNotice;
    private javax.swing.JButton btnBookRequest;
    private javax.swing.JButton btnBookingButton;
    private javax.swing.JButton btnClearRequest;
    private javax.swing.JButton btnDeleteBooking;
    private javax.swing.JButton btnDeleteNotice;
    private javax.swing.JButton btnDeleteRequest;
    private javax.swing.JButton btnHeroBtn;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnLoginBtn;
    private javax.swing.JButton btnLoginMain;
    private javax.swing.JButton btnNewBooking;
    private javax.swing.JButton btnReturnHome;
    private javax.swing.JButton btnUpdateBooking;
    private javax.swing.JButton btnUpdateNotice;
    private javax.swing.JComboBox<String> cmbBoxAnnouncementSort;
    private javax.swing.JDialog diaBookingDialog;
    private javax.swing.JLabel imgAdminLogo;
    private javax.swing.JLabel imgBookingCourtImage1;
    private javax.swing.JLabel imgCourtImage;
    private javax.swing.JLabel imgHeroImg;
    private javax.swing.JLabel imgLoginLog;
    private javax.swing.JLabel imgLogo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAdminBookingError;
    private javax.swing.JLabel lblAdminDashBoardTitle;
    private javax.swing.JLabel lblAnnouncementSort;
    private javax.swing.JLabel lblAnnouncementTitle;
    private javax.swing.JLabel lblBookingCourt;
    private javax.swing.JLabel lblBookingDate;
    private javax.swing.JLabel lblBookingDialogTitle;
    private javax.swing.JLabel lblBookingError;
    private javax.swing.JLabel lblBookingName;
    private javax.swing.JLabel lblBookingPhoneNumber;
    private javax.swing.JLabel lblBookingRequest;
    private javax.swing.JLabel lblBookingRequestTitleAdmin;
    private javax.swing.JLabel lblBookingTime;
    private javax.swing.JLabel lblBookingTitle;
    private javax.swing.JLabel lblBookings;
    private javax.swing.JLabel lblCourt;
    private javax.swing.JLabel lblCustomerName;
    private javax.swing.JLabel lblCustomerName1;
    private javax.swing.JLabel lblCustomerPhone;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblLoginError;
    private javax.swing.JLabel lblLoginPassword;
    private javax.swing.JLabel lblLoginPicture;
    private javax.swing.JLabel lblLoginUsername;
    private javax.swing.JLabel lblNavbarAnnouncement;
    private javax.swing.JLabel lblNavbarBooking;
    private javax.swing.JLabel lblNavbarHomelbl;
    private javax.swing.JLabel lblNewBooking;
    private javax.swing.JLabel lblNotice;
    private javax.swing.JLabel lblNoticeBy;
    private javax.swing.JLabel lblNoticeDate;
    private javax.swing.JLabel lblNoticeError;
    private javax.swing.JLabel lblNoticeHeading;
    private javax.swing.JLabel lblNoticeId;
    private javax.swing.JLabel lblNoticeTitle;
    private javax.swing.JLabel lblRequestCounter;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblnewbooking;
    private javax.swing.JList<String> listAnnouncement;
    private javax.swing.JList<String> listNoticeListAdmin;
    private javax.swing.JPasswordField passFldLoginPassword;
    private javax.swing.JPanel pnlAdmin;
    private javax.swing.JPanel pnlAdminCard;
    private javax.swing.JPanel pnlAdminNavbar;
    private javax.swing.JPanel pnlAnnouncement;
    private javax.swing.JPanel pnlAnnouncementPage;
    private javax.swing.JPanel pnlBookingMain;
    private javax.swing.JPanel pnlBookingPage;
    private javax.swing.JPanel pnlCardBooking;
    private javax.swing.JPanel pnlCardDashboard;
    private javax.swing.JPanel pnlCardNotice;
    private javax.swing.JPanel pnlDashboardNewBooking;
    private javax.swing.JPanel pnlHomePage;
    private javax.swing.JPanel pnlLoginMain;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMainCard;
    private javax.swing.JPanel pnlMainHero;
    private javax.swing.JPanel pnlMainNavbar;
    private javax.swing.JPanel pnlMainScreen;
    private javax.swing.JPanel pnlServicesSection;
    private javax.swing.JScrollPane sclPaneBookingRequest;
    private javax.swing.JScrollPane sclPaneBookingRequest1;
    private javax.swing.JScrollPane sclPaneBookingRequest2;
    private javax.swing.JScrollPane sclPaneCustomerPage;
    private javax.swing.JScrollPane sclPaneNotice;
    private javax.swing.JTable tblBookingCardRequest;
    private javax.swing.JTable tblBookingRequest;
    private javax.swing.JTable tblBookings;
    private javax.swing.JTextArea txtAreaHeroDesc;
    private javax.swing.JTextArea txtAreaHeroDesc1;
    private javax.swing.JTextArea txtAreaHeroTitle;
    private javax.swing.JTextArea txtAreaHeroTitle2;
    private javax.swing.JTextArea txtAreaNotice;
    private javax.swing.JTextField txtFldBookingCourtNo;
    private javax.swing.JTextField txtFldBookingDate;
    private javax.swing.JTextField txtFldBookingName;
    private javax.swing.JTextField txtFldBookingPhone;
    private javax.swing.JTextField txtFldBookingTime;
    private javax.swing.JTextField txtFldCourt;
    private javax.swing.JTextField txtFldCustomerId;
    private javax.swing.JTextField txtFldDate;
    private javax.swing.JTextField txtFldName;
    private javax.swing.JTextField txtFldNoticeBy;
    private javax.swing.JTextField txtFldNoticeDate;
    private javax.swing.JTextField txtFldNoticeHeading;
    private javax.swing.JTextField txtFldNoticeNo;
    private javax.swing.JTextField txtFldPhone;
    private javax.swing.JTextField txtFldTime;
    private javax.swing.JTextField txtFldUsername;
    // End of variables declaration//GEN-END:variables
}
