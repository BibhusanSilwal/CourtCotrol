/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.courtcontrol.views;

import com.courtcontrol.controllers.algorithms.Search;
import com.courtcontrol.controllers.algorithms.Sort;
import com.courtcontrol.controllers.datastructures.CustomQueue;
import com.courtcontrol.models.CustomerModel;
import com.courtcontrol.models.NoticeModel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Bibhusan Silwal LMU ID: 23048642
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

    /**
     * Adds the user booking request from the Customer side of the app to show
     * it in admin side.
     *
     * @param request the booking request by Customer
     *
     */
    public void addRequest(CustomerModel request) {
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

    /**
     * Deletes the first request from the request queue and removes its
     * corresponding rows from the booking request and booking card request
     * tables. Prints a message if the queue is empty.
     */
    public void deleteRequest() {
        if (requestQueue.isEmpty()) {
            System.out.print("The queue is empty");
            return;
        }

        int id = requestQueue.peek().getCustomerId();
        requestQueue.dequeue();
        DefaultTableModel model = (DefaultTableModel) tblBookingRequest.getModel();
        DefaultTableModel model2 = (DefaultTableModel) tblBookingCardRequest.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            Object Id = model.getValueAt(i, 0);
            if (Id instanceof Integer && (Integer) Id == id) {
                model.removeRow(i);
                model2.removeRow(i);
                return;
            }
        }

    }

    /**
     * Adds a new booking to the booking list and updates the bookings table and
     * request counter.
     *
     * @param booking The booking details to be added, represented by a
     * CustomerModel object.
     */
    public void addBooking(CustomerModel booking) {
        bookingList.add(booking);
        lblRequestCounter.setText(String.valueOf(bookingList.size()));
        DefaultTableModel model = (DefaultTableModel) tblBookings.getModel();
        model.addRow(new Object[]{
            booking.getCustomerId(), booking.getCustomerName(), booking.getCustomerPhone(), booking.getCustomerCourtNo(),
            booking.getCustomerDate(), booking.getCustomerTime()
        });
    }

    /**
     * Updates an existing booking in the bookings table and the booking list.
     *
     * @param updatedBooking The updated booking details, represented by a
     * CustomerModel object. The booking is identified by its customer ID.
     */
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

    /**
     * Deletes a booking from the bookings table and the booking list based on
     * the customer ID. Updates the request counter after the booking is
     * removed.
     *
     * @param customerId The ID of the customer whose booking is to be deleted.
     */
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

    /**
     * Adds a new notice to the notice list and updates the displayed notice
     * list.
     *
     * @param notice The notice details to be added, represented by a
     * NoticeModel object.
     */
    public void addNotice(NoticeModel notice) {
        noticeList.add(notice);
        updateNoticeList();
    }

    /**
     * Updates an existing notice in the notice list based on its notice ID and
     * refreshes the displayed notice list.
     *
     * @param notice The updated notice details, represented by a NoticeModel
     * object.
     */
    public void updateNotice(NoticeModel notice) {

        for (int i = 0; i < noticeList.size(); i++) {
            if (noticeList.get(i).getNoticeId() == notice.getNoticeId()) {
                noticeList.set(i, notice);
                break;
            }
        }
        updateNoticeList();
    }

    /**
     * Deletes a notice from the notice list based on its unique notice ID.
     *
     * This method iterates through the list of notices, finds the notice that
     * matches the provided noticeId, removes it from the list, and then updates
     * the displayed notice list to reflect the change.
     *
     * @param noticeId The unique ID of the notice to be deleted.
     */
    public void deleteNotice(int noticeId) {

        for (int i = 0; i < noticeList.size(); i++) {
            if (noticeList.get(i).getNoticeId() == noticeId) {
                noticeList.remove(i);
                break;
            }
        }
        updateNoticeList();
    }

    /**
     * Updates the notice list models to reflect the current data in the
     * noticeList.
     *
     * This method creates two separate `DefaultListModel` objects to represent
     * the list of notices. One model is used for displaying notices with
     * complete information (including ID and "By" field), while the other model
     * is used for displaying notices without the ID. The models are then set to
     * the corresponding list views for display in the user interface.
     */
    private void updateNoticeList() {
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
        lblAdminDashBoardTitle = new javax.swing.JLabel();
        pnlBookingRequest = new javax.swing.JPanel();
        lblBookingRequest = new javax.swing.JLabel();
        btnDeleteRequest = new javax.swing.JButton();
        sclPaneBookingRequest = new javax.swing.JScrollPane();
        tblBookingRequest = new javax.swing.JTable();
        pnlDashboardBooking = new javax.swing.JPanel();
        sclPaneBookingRequest1 = new javax.swing.JScrollPane();
        tblBookings = new javax.swing.JTable();
        lblBookings = new javax.swing.JLabel();
        cmbSortTable = new javax.swing.JComboBox<>();
        lblSearchState = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtFldSearchBooking = new javax.swing.JTextField();
        imgSearchIcon = new javax.swing.JLabel();
        pnlCardBooking = new javax.swing.JPanel();
        lblNewBooking = new javax.swing.JLabel();
        pnlBookingForm = new javax.swing.JPanel();
        txtFldTime = new javax.swing.JTextField();
        lblTime = new javax.swing.JLabel();
        txtFldPhone = new javax.swing.JTextField();
        lblCustomerPhone = new javax.swing.JLabel();
        txtFldName = new javax.swing.JTextField();
        lblCustomerName = new javax.swing.JLabel();
        txtFldDate = new javax.swing.JTextField();
        lblDate = new javax.swing.JLabel();
        txtFldCustomerId = new javax.swing.JTextField();
        lblCustomerName1 = new javax.swing.JLabel();
        txtFldCourt = new javax.swing.JTextField();
        lblCourt = new javax.swing.JLabel();
        btnDeleteBooking = new javax.swing.JButton();
        btnUpdateBooking = new javax.swing.JButton();
        btnNewBooking = new javax.swing.JButton();
        lblAdminBookingError = new javax.swing.JLabel();
        pnlBookingRequestTableBooking = new javax.swing.JPanel();
        sclPaneBookingRequest2 = new javax.swing.JScrollPane();
        tblBookingCardRequest = new javax.swing.JTable();
        lblBookingRequestTitleAdmin = new javax.swing.JLabel();
        pnlCardNotice = new javax.swing.JPanel();
        lblNoticeTitle = new javax.swing.JLabel();
        pnlNoticeForm = new javax.swing.JPanel();
        txtFldNoticeHeading = new javax.swing.JTextField();
        lblNoticeHeading = new javax.swing.JLabel();
        sclPaneNotice = new javax.swing.JScrollPane();
        txtAreaNotice = new javax.swing.JTextArea();
        lblNotice = new javax.swing.JLabel();
        lblNoticeError = new javax.swing.JLabel();
        btnDeleteNotice = new javax.swing.JButton();
        btnUpdateNotice = new javax.swing.JButton();
        btnAddNotice = new javax.swing.JButton();
        txtFldNoticeBy = new javax.swing.JTextField();
        lblNoticeBy = new javax.swing.JLabel();
        txtFldNoticeDate = new javax.swing.JTextField();
        lblNoticeDate = new javax.swing.JLabel();
        txtFldNoticeNo = new javax.swing.JTextField();
        lblNoticeId = new javax.swing.JLabel();
        pnlNoticeBoard = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listNoticeListAdmin = new javax.swing.JList<>();
        lblNoticeList = new javax.swing.JLabel();
        pnlMainScreen = new javax.swing.JPanel();
        pnlMain = new javax.swing.JPanel();
        pnlMainNavbar = new javax.swing.JPanel();
        lblNavbarHomelbl = new javax.swing.JLabel();
        lblNavbarBooking = new javax.swing.JLabel();
        lblNavbarAnnouncement = new javax.swing.JLabel();
        btnLoginMain = new javax.swing.JButton();
        imgLogo = new javax.swing.JLabel();
        pnlMainCard = new javax.swing.JPanel();
        pnlHomePage = new javax.swing.JPanel();
        sclPaneCustomerPage = new javax.swing.JScrollPane();
        pnlHomePagePanel = new javax.swing.JPanel();
        pnlMainHero = new javax.swing.JPanel();
        txtAreaHeroTitle = new javax.swing.JTextArea();
        txtAreaHeroTitle.setBackground(null);
        txtAreaHeroTitle2 = new javax.swing.JTextArea();
        txtAreaHeroDesc = new javax.swing.JTextArea();
        btnHeroBtn = new javax.swing.JButton();
        imgHeroImg = new javax.swing.JLabel();
        pnlServicesSection = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        imgCourtImage = new javax.swing.JLabel();
        lblServicesFood = new javax.swing.JLabel();
        imgFoodCourt = new javax.swing.JLabel();
        lblServicesOtherActivities = new javax.swing.JLabel();
        imgFitnessCenter = new javax.swing.JLabel();
        lblServicesTitle = new javax.swing.JLabel();
        txtAreaServicesCourt = new javax.swing.JTextArea();
        txtAreaServicesFood = new javax.swing.JTextArea();
        txtAreaServicesFood1 = new javax.swing.JTextArea();
        pnlBookingPage = new javax.swing.JPanel();
        pnlBookingMain = new javax.swing.JPanel();
        imgBookingCourtImage1 = new javax.swing.JLabel();
        txtAreaHeroDesc1 = new javax.swing.JTextArea();
        btnBookingButton = new javax.swing.JButton();
        lblBookingTitle = new javax.swing.JLabel();
        pnlAnnouncementPage = new javax.swing.JPanel();
        pnlAnnouncement = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listAnnouncement = new javax.swing.JList<>();
        lblAnnouncementTitle = new javax.swing.JLabel();

        pnlLoginMain.setBackground(new java.awt.Color(0, 0, 0));
        pnlLoginMain.setForeground(new java.awt.Color(255, 255, 255));
        pnlLoginMain.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlLoginMain.setLayout(null);

        lblLoginPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/resources/images/no backgroundsmall.png"))); // NOI18N
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

        imgLoginLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/resources/images/150logo.png"))); // NOI18N
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

        pnlAdminNavbar.setBackground(new java.awt.Color(255, 255, 255));
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

        imgAdminLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/resources/images/150logo.png"))); // NOI18N
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

        pnlCardDashboard.setBackground(new java.awt.Color(227, 228, 229));

        pnlDashboardNewBooking.setBackground(new java.awt.Color(255, 255, 255));

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

        lblAdminDashBoardTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblAdminDashBoardTitle.setForeground(new java.awt.Color(102, 102, 102));
        lblAdminDashBoardTitle.setText("Admin Dashboard");

        pnlBookingRequest.setBackground(new java.awt.Color(255, 255, 255));

        lblBookingRequest.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblBookingRequest.setForeground(new java.awt.Color(102, 102, 102));
        lblBookingRequest.setText("Booking Requests");

        btnDeleteRequest.setBackground(new java.awt.Color(255, 151, 141));
        btnDeleteRequest.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeleteRequest.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteRequest.setText("Delete Request");
        btnDeleteRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteRequestActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout pnlBookingRequestLayout = new javax.swing.GroupLayout(pnlBookingRequest);
        pnlBookingRequest.setLayout(pnlBookingRequestLayout);
        pnlBookingRequestLayout.setHorizontalGroup(
            pnlBookingRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBookingRequestLayout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(btnDeleteRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBookingRequestLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(pnlBookingRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBookingRequestLayout.createSequentialGroup()
                        .addComponent(lblBookingRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(203, 203, 203))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBookingRequestLayout.createSequentialGroup()
                        .addComponent(sclPaneBookingRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))))
        );
        pnlBookingRequestLayout.setVerticalGroup(
            pnlBookingRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBookingRequestLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(lblBookingRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(sclPaneBookingRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        pnlDashboardBooking.setBackground(new java.awt.Color(255, 255, 255));
        pnlDashboardBooking.setLayout(null);

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

        pnlDashboardBooking.add(sclPaneBookingRequest1);
        sclPaneBookingRequest1.setBounds(20, 169, 506, 407);

        lblBookings.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblBookings.setForeground(new java.awt.Color(102, 102, 102));
        lblBookings.setText("Bookings");
        pnlDashboardBooking.add(lblBookings);
        lblBookings.setBounds(220, 10, 164, 46);

        cmbSortTable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SortBy :", "ID", "Name", "Date" }));
        cmbSortTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSortTableActionPerformed(evt);
            }
        });
        pnlDashboardBooking.add(cmbSortTable);
        cmbSortTable.setBounds(30, 80, 100, 30);

        lblSearchState.setBackground(new java.awt.Color(255, 255, 255));
        lblSearchState.setForeground(new java.awt.Color(255, 0, 0));
        pnlDashboardBooking.add(lblSearchState);
        lblSearchState.setBounds(260, 130, 220, 30);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtFldSearchBooking.setBorder(null);
        txtFldSearchBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldSearchBookingActionPerformed(evt);
            }
        });

        imgSearchIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/resources/images/searchsmall.png"))); // NOI18N
        imgSearchIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgSearchIconMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(txtFldSearchBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imgSearchIcon)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtFldSearchBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imgSearchIcon)
                .addContainerGap())
        );

        pnlDashboardBooking.add(jPanel1);
        jPanel1.setBounds(250, 70, 280, 50);

        javax.swing.GroupLayout pnlCardDashboardLayout = new javax.swing.GroupLayout(pnlCardDashboard);
        pnlCardDashboard.setLayout(pnlCardDashboardLayout);
        pnlCardDashboardLayout.setHorizontalGroup(
            pnlCardDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardDashboardLayout.createSequentialGroup()
                .addGap(530, 530, 530)
                .addComponent(lblAdminDashBoardTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlCardDashboardLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlCardDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardDashboardLayout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(pnlDashboardNewBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlBookingRequest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(pnlDashboardBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlCardDashboardLayout.setVerticalGroup(
            pnlCardDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardDashboardLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblAdminDashBoardTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(pnlCardDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCardDashboardLayout.createSequentialGroup()
                        .addComponent(pnlDashboardNewBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(pnlBookingRequest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCardDashboardLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(pnlDashboardBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pnlAdminCard.add(pnlCardDashboard, "card2");

        pnlCardBooking.setBackground(new java.awt.Color(227, 228, 229));
        pnlCardBooking.setPreferredSize(new java.awt.Dimension(1200, 800));
        pnlCardBooking.setLayout(null);

        lblNewBooking.setBackground(new java.awt.Color(255, 255, 255));
        lblNewBooking.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblNewBooking.setForeground(new java.awt.Color(102, 102, 102));
        lblNewBooking.setText("Manage Booking");
        pnlCardBooking.add(lblNewBooking);
        lblNewBooking.setBounds(500, 80, 200, 34);

        pnlBookingForm.setBackground(new java.awt.Color(255, 255, 255));

        txtFldTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldTimeActionPerformed(evt);
            }
        });

        lblTime.setBackground(new java.awt.Color(0, 0, 0));
        lblTime.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTime.setForeground(new java.awt.Color(102, 102, 102));
        lblTime.setText("Time");

        txtFldPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldPhoneActionPerformed(evt);
            }
        });

        lblCustomerPhone.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCustomerPhone.setForeground(new java.awt.Color(102, 102, 102));
        lblCustomerPhone.setText("Phone");

        txtFldName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldNameActionPerformed(evt);
            }
        });

        lblCustomerName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCustomerName.setForeground(new java.awt.Color(102, 102, 102));
        lblCustomerName.setText("Customer Name");

        txtFldDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldDateActionPerformed(evt);
            }
        });

        lblDate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDate.setForeground(new java.awt.Color(102, 102, 102));
        lblDate.setText("Date");

        txtFldCustomerId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldCustomerIdActionPerformed(evt);
            }
        });

        lblCustomerName1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCustomerName1.setForeground(new java.awt.Color(102, 102, 102));
        lblCustomerName1.setText("Customer Id");

        txtFldCourt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFldCourtActionPerformed(evt);
            }
        });

        lblCourt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCourt.setForeground(new java.awt.Color(102, 102, 102));
        lblCourt.setText("Court");

        btnDeleteBooking.setBackground(new java.awt.Color(36, 74, 138));
        btnDeleteBooking.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeleteBooking.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteBooking.setText("Delete Booking");
        btnDeleteBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteBookingActionPerformed(evt);
            }
        });

        btnUpdateBooking.setBackground(new java.awt.Color(36, 74, 138));
        btnUpdateBooking.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateBooking.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateBooking.setText("Update Booking");
        btnUpdateBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateBookingActionPerformed(evt);
            }
        });

        btnNewBooking.setBackground(new java.awt.Color(36, 74, 138));
        btnNewBooking.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewBooking.setForeground(new java.awt.Color(255, 255, 255));
        btnNewBooking.setText("Add Booking");
        btnNewBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewBookingActionPerformed(evt);
            }
        });

        lblAdminBookingError.setForeground(new java.awt.Color(255, 0, 0));
        lblAdminBookingError.setPreferredSize(new java.awt.Dimension(80, 20));

        javax.swing.GroupLayout pnlBookingFormLayout = new javax.swing.GroupLayout(pnlBookingForm);
        pnlBookingForm.setLayout(pnlBookingFormLayout);
        pnlBookingFormLayout.setHorizontalGroup(
            pnlBookingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBookingFormLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(pnlBookingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBookingFormLayout.createSequentialGroup()
                        .addComponent(lblAdminBookingError, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlBookingFormLayout.createSequentialGroup()
                        .addGroup(pnlBookingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustomerName1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFldCustomerId, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFldCourt, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCourt, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNewBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(pnlBookingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlBookingFormLayout.createSequentialGroup()
                                .addComponent(lblCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(lblCustomerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(pnlBookingFormLayout.createSequentialGroup()
                                .addGroup(pnlBookingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFldName, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFldDate, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdateBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(43, 43, 43)
                                .addGroup(pnlBookingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlBookingFormLayout.createSequentialGroup()
                                        .addComponent(txtFldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(29, Short.MAX_VALUE))
                                    .addGroup(pnlBookingFormLayout.createSequentialGroup()
                                        .addGroup(pnlBookingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtFldTime, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnDeleteBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))))))
        );
        pnlBookingFormLayout.setVerticalGroup(
            pnlBookingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBookingFormLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(pnlBookingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomerName1)
                    .addComponent(lblCustomerName)
                    .addComponent(lblCustomerPhone))
                .addGap(18, 18, 18)
                .addGroup(pnlBookingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFldCustomerId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFldName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlBookingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBookingFormLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(pnlBookingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTime)
                            .addComponent(lblDate))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBookingFormLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCourt)
                        .addGap(26, 26, 26)))
                .addGroup(pnlBookingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFldDate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFldTime, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFldCourt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(lblAdminBookingError, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(pnlBookingFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        pnlCardBooking.add(pnlBookingForm);
        pnlBookingForm.setBounds(620, 210, 530, 470);

        pnlBookingRequestTableBooking.setBackground(new java.awt.Color(255, 255, 255));

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

        lblBookingRequestTitleAdmin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblBookingRequestTitleAdmin.setForeground(new java.awt.Color(102, 102, 102));
        lblBookingRequestTitleAdmin.setText("Booking Request Table");

        javax.swing.GroupLayout pnlBookingRequestTableBookingLayout = new javax.swing.GroupLayout(pnlBookingRequestTableBooking);
        pnlBookingRequestTableBooking.setLayout(pnlBookingRequestTableBookingLayout);
        pnlBookingRequestTableBookingLayout.setHorizontalGroup(
            pnlBookingRequestTableBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBookingRequestTableBookingLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(pnlBookingRequestTableBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sclPaneBookingRequest2, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBookingRequestTitleAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        pnlBookingRequestTableBookingLayout.setVerticalGroup(
            pnlBookingRequestTableBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBookingRequestTableBookingLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(lblBookingRequestTitleAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(sclPaneBookingRequest2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        pnlCardBooking.add(pnlBookingRequestTableBooking);
        pnlBookingRequestTableBooking.setBounds(50, 210, 520, 470);

        pnlAdminCard.add(pnlCardBooking, "card3");

        pnlCardNotice.setBackground(new java.awt.Color(227, 228, 229));
        pnlCardNotice.setPreferredSize(new java.awt.Dimension(1200, 800));
        pnlCardNotice.setLayout(null);

        lblNoticeTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblNoticeTitle.setForeground(new java.awt.Color(51, 51, 51));
        lblNoticeTitle.setText("Notice");
        pnlCardNotice.add(lblNoticeTitle);
        lblNoticeTitle.setBounds(570, 50, 90, 67);

        pnlNoticeForm.setBackground(new java.awt.Color(255, 255, 255));
        pnlNoticeForm.setLayout(null);
        pnlNoticeForm.add(txtFldNoticeHeading);
        txtFldNoticeHeading.setBounds(30, 270, 180, 33);

        lblNoticeHeading.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoticeHeading.setForeground(new java.awt.Color(102, 102, 102));
        lblNoticeHeading.setText("Notice Heading");
        pnlNoticeForm.add(lblNoticeHeading);
        lblNoticeHeading.setBounds(30, 240, 117, 16);

        txtAreaNotice.setColumns(20);
        txtAreaNotice.setRows(5);
        txtAreaNotice.setBorder(null);
        sclPaneNotice.setViewportView(txtAreaNotice);

        pnlNoticeForm.add(sclPaneNotice);
        sclPaneNotice.setBounds(280, 260, 250, 100);

        lblNotice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNotice.setForeground(new java.awt.Color(102, 102, 102));
        lblNotice.setText("Notice");
        pnlNoticeForm.add(lblNotice);
        lblNotice.setBounds(280, 240, 117, 16);

        lblNoticeError.setForeground(new java.awt.Color(255, 0, 0));
        lblNoticeError.setPreferredSize(new java.awt.Dimension(80, 20));
        pnlNoticeForm.add(lblNoticeError);
        lblNoticeError.setBounds(40, 380, 290, 30);

        btnDeleteNotice.setBackground(new java.awt.Color(36, 74, 138));
        btnDeleteNotice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeleteNotice.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteNotice.setText("Delete Notice");
        btnDeleteNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteNoticeActionPerformed(evt);
            }
        });
        pnlNoticeForm.add(btnDeleteNotice);
        btnDeleteNotice.setBounds(370, 470, 125, 35);

        btnUpdateNotice.setBackground(new java.awt.Color(36, 74, 138));
        btnUpdateNotice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateNotice.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateNotice.setText("Update Notice");
        btnUpdateNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateNoticeActionPerformed(evt);
            }
        });
        pnlNoticeForm.add(btnUpdateNotice);
        btnUpdateNotice.setBounds(190, 470, 125, 35);

        btnAddNotice.setBackground(new java.awt.Color(36, 74, 138));
        btnAddNotice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddNotice.setForeground(new java.awt.Color(255, 255, 255));
        btnAddNotice.setText("Add Notice");
        btnAddNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNoticeActionPerformed(evt);
            }
        });
        pnlNoticeForm.add(btnAddNotice);
        btnAddNotice.setBounds(30, 470, 125, 35);
        pnlNoticeForm.add(txtFldNoticeBy);
        txtFldNoticeBy.setBounds(370, 120, 160, 40);

        lblNoticeBy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoticeBy.setForeground(new java.awt.Color(102, 102, 102));
        lblNoticeBy.setText("Notice By");
        pnlNoticeForm.add(lblNoticeBy);
        lblNoticeBy.setBounds(360, 90, 117, 16);
        pnlNoticeForm.add(txtFldNoticeDate);
        txtFldNoticeDate.setBounds(210, 120, 120, 40);

        lblNoticeDate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoticeDate.setForeground(new java.awt.Color(102, 102, 102));
        lblNoticeDate.setText("Notice Date");
        pnlNoticeForm.add(lblNoticeDate);
        lblNoticeDate.setBounds(210, 90, 117, 16);
        pnlNoticeForm.add(txtFldNoticeNo);
        txtFldNoticeNo.setBounds(20, 120, 170, 40);

        lblNoticeId.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNoticeId.setForeground(new java.awt.Color(102, 102, 102));
        lblNoticeId.setText("Notice Id");
        pnlNoticeForm.add(lblNoticeId);
        lblNoticeId.setBounds(30, 90, 117, 16);

        pnlCardNotice.add(pnlNoticeForm);
        pnlNoticeForm.setBounds(610, 210, 550, 540);

        pnlNoticeBoard.setBackground(new java.awt.Color(255, 255, 255));
        pnlNoticeBoard.setLayout(null);

        listNoticeListAdmin.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listNoticeListAdmin);

        pnlNoticeBoard.add(jScrollPane1);
        jScrollPane1.setBounds(30, 100, 480, 380);

        lblNoticeList.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNoticeList.setForeground(new java.awt.Color(102, 102, 102));
        lblNoticeList.setText("Notice List");
        pnlNoticeBoard.add(lblNoticeList);
        lblNoticeList.setBounds(40, 30, 130, 40);

        pnlCardNotice.add(pnlNoticeBoard);
        pnlNoticeBoard.setBounds(30, 210, 550, 540);

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1400, 800));
        setResizable(false);
        setSize(new java.awt.Dimension(1400, 800));

        pnlMainScreen.setBackground(new java.awt.Color(245, 246, 250));
        pnlMainScreen.setPreferredSize(new java.awt.Dimension(1400, 800));

        pnlMain.setBackground(new java.awt.Color(227, 228, 229));
        pnlMain.setLayout(null);

        pnlMainNavbar.setBackground(new java.awt.Color(255, 255, 255));
        pnlMainNavbar.setForeground(new java.awt.Color(255, 255, 255));
        pnlMainNavbar.setPreferredSize(new java.awt.Dimension(1400, 66));
        pnlMainNavbar.setLayout(null);

        lblNavbarHomelbl.setBackground(new java.awt.Color(255, 255, 255));
        lblNavbarHomelbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNavbarHomelbl.setForeground(new java.awt.Color(102, 102, 102));
        lblNavbarHomelbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNavbarHomelbl.setText("Home");
        lblNavbarHomelbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblNavbarHomelbl.setOpaque(true);
        lblNavbarHomelbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNavbarHomelblMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblNavbarHomelblMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblNavbarHomelblMouseExited(evt);
            }
        });
        pnlMainNavbar.add(lblNavbarHomelbl);
        lblNavbarHomelbl.setBounds(610, -20, 110, 110);

        lblNavbarBooking.setBackground(new java.awt.Color(255, 255, 255));
        lblNavbarBooking.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNavbarBooking.setForeground(new java.awt.Color(102, 102, 102));
        lblNavbarBooking.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNavbarBooking.setText("Booking");
        lblNavbarBooking.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblNavbarBooking.setOpaque(true);
        lblNavbarBooking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNavbarBookingMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblNavbarBookingMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblNavbarBookingMouseExited(evt);
            }
        });
        pnlMainNavbar.add(lblNavbarBooking);
        lblNavbarBooking.setBounds(730, 0, 120, 70);

        lblNavbarAnnouncement.setBackground(new java.awt.Color(255, 255, 255));
        lblNavbarAnnouncement.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNavbarAnnouncement.setForeground(new java.awt.Color(102, 102, 102));
        lblNavbarAnnouncement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNavbarAnnouncement.setText("Announcement");
        lblNavbarAnnouncement.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblNavbarAnnouncement.setOpaque(true);
        lblNavbarAnnouncement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNavbarAnnouncementMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblNavbarAnnouncementMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblNavbarAnnouncementMouseExited(evt);
            }
        });
        pnlMainNavbar.add(lblNavbarAnnouncement);
        lblNavbarAnnouncement.setBounds(850, 0, 120, 70);

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

        imgLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/resources/images/HoopsHeaven.png"))); // NOI18N
        pnlMain.add(imgLogo);
        imgLogo.setBounds(40, 10, 100, 101);

        pnlMainCard.setBackground(new java.awt.Color(245, 246, 250));
        pnlMainCard.setPreferredSize(new java.awt.Dimension(1400, 680));
        pnlMainCard.setLayout(new java.awt.CardLayout());

        pnlHomePage.setBackground(new java.awt.Color(227, 228, 229));
        pnlHomePage.setPreferredSize(new java.awt.Dimension(1400, 680));

        sclPaneCustomerPage.setBackground(new java.awt.Color(153, 153, 153));
        sclPaneCustomerPage.setBorder(null);
        sclPaneCustomerPage.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sclPaneCustomerPage.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sclPaneCustomerPage.setPreferredSize(new java.awt.Dimension(1400, 680));
        sclPaneCustomerPage.setViewportView(pnlMain);

        pnlHomePagePanel.setBackground(new java.awt.Color(227, 228, 229));
        pnlHomePagePanel.setPreferredSize(new java.awt.Dimension(1400, 1500));

        pnlMainHero.setBackground(new java.awt.Color(255, 255, 255));
        pnlMainHero.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlMainHero.setPreferredSize(new java.awt.Dimension(1400, 547));
        pnlMainHero.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtAreaHeroTitle.setEditable(false);
        txtAreaHeroTitle.setBackground(new Color(0,0,0,0));
        txtAreaHeroTitle.setColumns(20);
        txtAreaHeroTitle.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        txtAreaHeroTitle.setForeground(new java.awt.Color(255, 255, 255));
        txtAreaHeroTitle.setRows(5);
        txtAreaHeroTitle.setText("It's Time to HOOP!");
        txtAreaHeroTitle.setBorder(null);
        txtAreaHeroTitle.setFocusable(false);
        txtAreaHeroTitle.setOpaque(false);
        pnlMainHero.add(txtAreaHeroTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 90, 450, 80));

        txtAreaHeroTitle2.setBackground(new Color(0,0,0,0));
        txtAreaHeroTitle2.setColumns(20);
        txtAreaHeroTitle2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtAreaHeroTitle2.setForeground(new java.awt.Color(255, 255, 255));
        txtAreaHeroTitle2.setRows(5);
        txtAreaHeroTitle2.setText("Experience the thrill of basketball at state of \nart basketball court at HOOPS HEAVEN.\n");
        txtAreaHeroTitle2.setBorder(null);
        txtAreaHeroTitle2.setFocusable(false);
        txtAreaHeroTitle2.setOpaque(false);
        pnlMainHero.add(txtAreaHeroTitle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 190, -1, 65));

        txtAreaHeroDesc.setBackground(new Color(0,0,0,0));
        txtAreaHeroDesc.setColumns(20);
        txtAreaHeroDesc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtAreaHeroDesc.setForeground(new java.awt.Color(255, 255, 255));
        txtAreaHeroDesc.setRows(5);
        txtAreaHeroDesc.setText("Whether it’s a one-on-one or a full-court battle, our facility offers the\nperfect environment for basketball lovers to play, train, and thrive.");
        txtAreaHeroDesc.setBorder(null);
        txtAreaHeroDesc.setFocusable(false);
        txtAreaHeroDesc.setOpaque(false);
        pnlMainHero.add(txtAreaHeroDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 290, 573, 65));

        btnHeroBtn.setBackground(new java.awt.Color(36, 74, 138));
        btnHeroBtn.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnHeroBtn.setForeground(new java.awt.Color(255, 255, 255));
        btnHeroBtn.setText("BOOK NOW");
        btnHeroBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHeroBtnActionPerformed(evt);
            }
        });
        pnlMainHero.add(btnHeroBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 390, -1, -1));

        imgHeroImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/resources/images/heroimageresize.jpg"))); // NOI18N
        pnlMainHero.add(imgHeroImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -230, -1, 790));

        pnlServicesSection.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Court Rentals");

        imgCourtImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/resources/images/servicescourt.jpg"))); // NOI18N

        lblServicesFood.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblServicesFood.setText("Food Courts");

        imgFoodCourt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/resources/images/foodcourt.jpg"))); // NOI18N

        lblServicesOtherActivities.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblServicesOtherActivities.setText("Fitness and Wellness Center");

        imgFitnessCenter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/resources/images/fitness.jpg"))); // NOI18N

        lblServicesTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblServicesTitle.setText("Our Services");

        txtAreaServicesCourt.setBackground(new Color(0,0,0,0));
        txtAreaServicesCourt.setColumns(20);
        txtAreaServicesCourt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtAreaServicesCourt.setRows(5);
        txtAreaServicesCourt.setText("Book state-of-the-art basketball courts for\n practice, games, or tournaments.\n Flexible hourly and full-day options available!");
        txtAreaServicesCourt.setBorder(null);
        txtAreaServicesCourt.setFocusable(false);
        txtAreaServicesCourt.setOpaque(false);

        txtAreaServicesFood.setBackground(new Color(0,0,0,0));
        txtAreaServicesFood.setColumns(20);
        txtAreaServicesFood.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtAreaServicesFood.setRows(5);
        txtAreaServicesFood.setText("Recharge after a game with delicious snacks \nand refreshing drinks at our on-site food court.");
        txtAreaServicesFood.setBorder(null);
        txtAreaServicesFood.setFocusable(false);
        txtAreaServicesFood.setOpaque(false);

        txtAreaServicesFood1.setBackground(new Color(0,0,0,0));
        txtAreaServicesFood1.setColumns(20);
        txtAreaServicesFood1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtAreaServicesFood1.setRows(5);
        txtAreaServicesFood1.setText("Enhance your game with specialized fitness training,\nrecovery zones, and wellness programs designed for \nElite ATHLETES.");
        txtAreaServicesFood1.setBorder(null);
        txtAreaServicesFood1.setFocusable(false);
        txtAreaServicesFood1.setOpaque(false);

        javax.swing.GroupLayout pnlServicesSectionLayout = new javax.swing.GroupLayout(pnlServicesSection);
        pnlServicesSection.setLayout(pnlServicesSectionLayout);
        pnlServicesSectionLayout.setHorizontalGroup(
            pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                .addGroup(pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                        .addGroup(pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                                .addGap(159, 159, 159)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(250, 250, 250)
                                .addComponent(lblServicesFood, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addGroup(pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtAreaServicesCourt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(imgCourtImage))
                                .addGap(106, 106, 106)
                                .addGroup(pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtAreaServicesFood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(imgFoodCourt))))
                        .addGap(109, 109, 109)
                        .addGroup(pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblServicesOtherActivities)
                            .addComponent(imgFitnessCenter, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAreaServicesFood1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                        .addGap(534, 534, 534)
                        .addComponent(lblServicesTitle)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlServicesSectionLayout.setVerticalGroup(
            pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                .addGroup(pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(lblServicesTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addGroup(pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblServicesFood, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblServicesOtherActivities, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlServicesSectionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)))
                .addGroup(pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgCourtImage)
                    .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(imgFitnessCenter))
                    .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(imgFoodCourt)))
                .addGroup(pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(pnlServicesSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAreaServicesCourt, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAreaServicesFood, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlServicesSectionLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(txtAreaServicesFood1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlHomePagePanelLayout = new javax.swing.GroupLayout(pnlHomePagePanel);
        pnlHomePagePanel.setLayout(pnlHomePagePanelLayout);
        pnlHomePagePanelLayout.setHorizontalGroup(
            pnlHomePagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomePagePanelLayout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(pnlHomePagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlMainHero, javax.swing.GroupLayout.PREFERRED_SIZE, 1235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlServicesSection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHomePagePanelLayout.setVerticalGroup(
            pnlHomePagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomePagePanelLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(pnlMainHero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(pnlServicesSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );

        sclPaneCustomerPage.setViewportView(pnlHomePagePanel);

        javax.swing.GroupLayout pnlHomePageLayout = new javax.swing.GroupLayout(pnlHomePage);
        pnlHomePage.setLayout(pnlHomePageLayout);
        pnlHomePageLayout.setHorizontalGroup(
            pnlHomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomePageLayout.createSequentialGroup()
                .addComponent(sclPaneCustomerPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlHomePageLayout.setVerticalGroup(
            pnlHomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHomePageLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sclPaneCustomerPage, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMainCard.add(pnlHomePage, "card2");

        pnlBookingPage.setBackground(new java.awt.Color(227, 228, 229));
        pnlBookingPage.setPreferredSize(new java.awt.Dimension(1400, 680));
        pnlBookingPage.setLayout(null);

        pnlBookingMain.setBackground(new java.awt.Color(255, 255, 255));
        pnlBookingMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        imgBookingCourtImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/courtcontrol/resources/images/500court.jpg"))); // NOI18N

        txtAreaHeroDesc1.setColumns(20);
        txtAreaHeroDesc1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtAreaHeroDesc1.setRows(5);
        txtAreaHeroDesc1.setText("Book your court effortlessly! Whether it's a friendly match or an intense practice session,\nour well-maintained courts are available for hourly or daily rentals.");
        txtAreaHeroDesc1.setBorder(null);
        txtAreaHeroDesc1.setFocusable(false);
        txtAreaHeroDesc1.setOpaque(false);

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

        lblBookingTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblBookingTitle.setText("Booking");

        javax.swing.GroupLayout pnlBookingMainLayout = new javax.swing.GroupLayout(pnlBookingMain);
        pnlBookingMain.setLayout(pnlBookingMainLayout);
        pnlBookingMainLayout.setHorizontalGroup(
            pnlBookingMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBookingMainLayout.createSequentialGroup()
                .addGroup(pnlBookingMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBookingMainLayout.createSequentialGroup()
                        .addGap(339, 339, 339)
                        .addComponent(lblBookingTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlBookingMainLayout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(imgBookingCourtImage1))
                    .addGroup(pnlBookingMainLayout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(txtAreaHeroDesc1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlBookingMainLayout.createSequentialGroup()
                        .addGap(279, 279, 279)
                        .addComponent(btnBookingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        pnlBookingMainLayout.setVerticalGroup(
            pnlBookingMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBookingMainLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lblBookingTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(imgBookingCourtImage1)
                .addGap(17, 17, 17)
                .addComponent(txtAreaHeroDesc1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnBookingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlBookingPage.add(pnlBookingMain);
        pnlBookingMain.setBounds(290, -10, 780, 630);

        pnlMainCard.add(pnlBookingPage, "card3");

        pnlAnnouncementPage.setBackground(new java.awt.Color(227, 228, 229));
        pnlAnnouncementPage.setPreferredSize(new java.awt.Dimension(1400, 680));
        pnlAnnouncementPage.setLayout(null);

        pnlAnnouncement.setBackground(new java.awt.Color(255, 255, 255));
        pnlAnnouncement.setPreferredSize(new java.awt.Dimension(1220, 510));
        pnlAnnouncement.setLayout(null);

        listAnnouncement.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(listAnnouncement);

        pnlAnnouncement.add(jScrollPane3);
        jScrollPane3.setBounds(130, 140, 520, 290);

        lblAnnouncementTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblAnnouncementTitle.setText("Announcement");
        pnlAnnouncement.add(lblAnnouncementTitle);
        lblAnnouncementTitle.setBounds(280, 30, 190, 40);

        pnlAnnouncementPage.add(pnlAnnouncement);
        pnlAnnouncement.setBounds(320, 50, 770, 510);

        pnlMainCard.add(pnlAnnouncementPage, "card4");

        pnlMain.add(pnlMainCard);
        pnlMainCard.setBounds(0, 120, 1690, 680);

        javax.swing.GroupLayout pnlMainScreenLayout = new javax.swing.GroupLayout(pnlMainScreen);
        pnlMainScreen.setLayout(pnlMainScreenLayout);
        pnlMainScreenLayout.setHorizontalGroup(
            pnlMainScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 1400, Short.MAX_VALUE)
        );
        pnlMainScreenLayout.setVerticalGroup(
            pnlMainScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainScreenLayout.createSequentialGroup()
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    /**
     * Initializes the layout of the application window by setting up the
     * CardLayout.
     *
     * This method configures the layout manager to use CardLayout and adds
     * different panels (MainScreen, LoginScreen, AdminScreen) to the content
     * pane. It then loads the MainScreen as the initial view of the
     * application.
     */
    private void initializeLayout() {
        cardLayout = new java.awt.CardLayout();
        getContentPane().setLayout(cardLayout);

        getContentPane().add(pnlMainScreen, "MainScreen");
        getContentPane().add(pnlLoginMain, "LoginScreen");
        getContentPane().add(pnlAdmin, "AdminScreen");

        loadScreen("MainScreen");

    }

    /**
     * Loads the specified screen in the CardLayout.
     *
     * This method uses the CardLayout to display the panel corresponding to the
     * given screen name by calling the `show` method on the content pane with
     * the provided screen name.
     *
     * @param screen The name of the screen to be displayed.
     */
    private void loadScreen(String screen) {
        cardLayout.show(getContentPane(), screen);
    }

    /**
     * Initializes the data structures and populates them with sample data.
     *
     * This method sets up the requestQueue, bookingList, and noticeList. It
     * then adds initial data to these lists, including customer requests,
     * bookings, and notices. This setup ensures that the system has pre-loaded
     * information for further use.
     */
    private void initializeData() {
        requestQueue = new CustomQueue(14);
        bookingList = new ArrayList();
        noticeList = new ArrayList();

        addRequest(new CustomerModel("Bibhusan", "9803639288", "10:00am", "2024-12-28", (short) 1));
        addRequest(new CustomerModel("Nirajan", "9841287660", "11:00am", "2024-12-28", (short) 1));

        addBooking(new CustomerModel(2, "Bibhusan", "9803639288", "10:00am", "2024-12-28", (short) 1));
        addBooking(new CustomerModel(1, "Pawan", "9746478675", "10:00am", "2024-12-28", (short) 1));

        addNotice(new NoticeModel(1, "Bibhusan Silwal", "2024-12-26", "Three Point Contest Announcement", "We are going to organize a three point contest with prizepool of 1 lakhs Rupees."));
    }


    private void txtFldUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldUsernameActionPerformed

    /**
     * Handles the action when the login button is clicked.
     *
     * This method retrieves the username and password entered by the user,
     * checks if they are valid, and provides feedback to the user. If the login
     * credentials are correct (username and password both equal to "admin"), it
     * clears the input fields and loads the AdminScreen. Otherwise, it displays
     * an error message.
     *
     * @param evt The event triggered by the login button click.
     */

    private void btnLoginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginBtnActionPerformed
        // TODO add your handling code here:
        String username = txtFldUsername.getText();
        String password = new String(passFldLoginPassword.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            lblLoginError.setText("Please enter your username and password.");
        } else if (!username.equals("admin") || !password.equals("admin")) {
            lblLoginError.setText("Username and password mismatch.");
        } else {
            lblLoginError.setText("");
            txtFldUsername.setText("");
            passFldLoginPassword.setText("");
            loadScreen("AdminScreen");
        }

    }//GEN-LAST:event_btnLoginBtnActionPerformed

    private void passFldLoginPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passFldLoginPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passFldLoginPasswordActionPerformed

    /**
     * Handles the action when the "Admin Notice" button is clicked.
     *
     * This method removes all existing components from the admin card panel,
     * adds the notice panel to it, and then repaints and revalidates the panel
     * to reflect the change in the user interface.
     *
     * @param evt The event triggered by the "Admin Notice" button click.
     */

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

    /**
     * Handles the action when the "Admin Dashboard" button is clicked.
     *
     * This method removes all existing components from the admin card panel,
     * adds the dashboard panel to it, and then repaints and revalidates the
     * panel to reflect the change in the user interface.
     *
     * @param evt The event triggered by the "Admin Dashboard" button click.
     */

    private void btnAdminDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminDashboardActionPerformed
        // TODO add your handling code here:
        pnlAdminCard.removeAll();
        pnlAdminCard.add(pnlCardDashboard);
        pnlAdminCard.repaint();
        pnlAdminCard.revalidate();
    }//GEN-LAST:event_btnAdminDashboardActionPerformed

    /**
     * Handles the action when the "Admin Booking" button is clicked.
     *
     * This method removes all existing components from the admin card panel,
     * adds the booking panel to it, and then repaints and revalidates the panel
     * to reflect the change in the user interface.
     *
     * @param evt The event triggered by the "Admin Booking" button click.
     */

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

    /**
     * Handles the action when the "Add Notice" button is clicked.
     *
     * This method retrieves the notice details entered by the user, validates
     * the input, checks if a notice with the same ID already exists, and adds
     * the notice to the list. If any required fields are empty or the notice ID
     * is invalid, it displays an appropriate error message. After successfully
     * adding the notice, it clears the input fields.
     *
     * @param evt The event triggered by the "Add Notice" button click.
     */

    private void btnAddNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNoticeActionPerformed
        // TODO add your handling code here:
        try {
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
            }
        } catch (NumberFormatException ex) {
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
            boolean exists = false;
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

                updateNotice(new NoticeModel(noticeId, noticeBy, noticeDate, noticeHeading, notice));

            } else {
                lblNoticeError.setText("Notice with this ID does not exist.");
            }
        }

    }//GEN-LAST:event_btnUpdateNoticeActionPerformed

    /**
     * Handles the action when the "Delete Notice" button is clicked.
     *
     * This method retrieves the notice ID entered by the user, checks if the ID
     * exists in the notice list, and deletes the notice if it exists. If the ID
     * is invalid or the notice does not exist, it displays an appropriate error
     * message. After successfully deleting the notice, it clears the input
     * field.
     *
     * @param evt The event triggered by the "Delete Notice" button click.
     */

    private void btnDeleteNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteNoticeActionPerformed
        // TODO add your handling code here:
        String NoticeIdText = txtFldNoticeNo.getText();
        try {
            if (NoticeIdText.isEmpty()) {
                lblAdminBookingError.setText("Notice ID is required.");
            } else {
                boolean exists = false;
                int noticeId = Integer.parseInt(txtFldNoticeNo.getText());
                for (NoticeModel existingNotice : noticeList) {
                    if (existingNotice.getNoticeId() == noticeId) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {

                    lblNoticeError.setText("");
                    txtFldNoticeNo.setText("");
                    deleteNotice(noticeId);

                } else {
                    lblNoticeError.setText("Notice Id doesn't Exists");
                }

            }
        } catch (NumberFormatException ex) {
            lblNoticeError.setText("Invalid Notice ID. Must be a number.");
        }
    }//GEN-LAST:event_btnDeleteNoticeActionPerformed

    /**
     * Handles the action when the "Navbar Booking" label is clicked.
     *
     * This method removes all existing components from the main card panel,
     * adds the booking page panel to it, and then repaints and revalidates the
     * panel to reflect the change in the user interface.
     *
     * @param evt The event triggered by the "Navbar Booking" label click.
     */

    private void lblNavbarBookingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNavbarBookingMouseClicked
        // TODO add your handling code here:
        pnlMainCard.removeAll();
        pnlMainCard.add(pnlBookingPage);
        pnlMainCard.repaint();
        pnlMainCard.revalidate();
    }//GEN-LAST:event_lblNavbarBookingMouseClicked

    /**
     * Handles the action when the "Navbar Announcement" label is clicked.
     *
     * This method removes all existing components from the main card panel,
     * adds the announcement page panel to it, and then repaints and revalidates
     * the panel to reflect the change in the user interface.
     *
     * @param evt The event triggered by the "Navbar Announcement" label click.
     */

    private void lblNavbarAnnouncementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNavbarAnnouncementMouseClicked
        // TODO add your handling code here:
        pnlMainCard.removeAll();
        pnlMainCard.add(pnlAnnouncementPage);
        pnlMainCard.repaint();
        pnlMainCard.revalidate();
    }//GEN-LAST:event_lblNavbarAnnouncementMouseClicked

    /**
     * Handles the action when the "Navbar Home" label is clicked.
     *
     * This method removes all existing components from the main card panel,
     * adds the home page panel to it, and then repaints and revalidates the
     * panel to reflect the change in the user interface.
     *
     * @param evt The event triggered by the "Navbar Home" label click.
     */

    private void lblNavbarHomelblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNavbarHomelblMouseClicked
        // TODO add your handling code here:
        pnlMainCard.removeAll();
        pnlMainCard.add(pnlHomePage);
        pnlMainCard.repaint();
        pnlMainCard.revalidate();
    }//GEN-LAST:event_lblNavbarHomelblMouseClicked

    /**
     * Handles the action when the "Booking Button" is clicked.
     *
     * This method makes the booking dialog visible, sets its location to the
     * center of the screen, and adjusts its size to 600x450 pixels.
     *
     * @param evt The event triggered by the "Booking Button" click.
     */

    private void btnBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookingButtonActionPerformed
        // TODO add your handling code here:
        diaBookingDialog.setVisible(true);
        diaBookingDialog.setLocationRelativeTo(null);
        diaBookingDialog.setSize(600, 450);
    }//GEN-LAST:event_btnBookingButtonActionPerformed

    private void txtFldBookingCourtNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldBookingCourtNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldBookingCourtNoActionPerformed

    /**
     * Handles the action when the "Clear Request" button is clicked.
     *
     * This method clears all input fields in the booking form, resetting them
     * to empty values to allow the user to start a new booking request without
     * previous data.
     *
     * @param evt The event triggered by the "Clear Request" button click.
     */

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

    /**
     * Handles the action when the "Login Main" button is clicked.
     *
     * This method switches the displayed screen to the login screen, allowing
     * the user to enter their credentials to access the system.
     *
     * @param evt The event triggered by the "Login Main" button click.
     */

    private void btnLoginMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginMainActionPerformed
        // TODO add your handling code here:
        loadScreen("LoginScreen");
    }//GEN-LAST:event_btnLoginMainActionPerformed

    /**
     * Handles the action when the "Book Request" button is clicked.
     *
     * This method validates the user's booking request by checking if all
     * fields are filled correctly. It ensures that the customer name, phone
     * number, booking date, time, and court number are provided, and that the
     * phone number and court number are in the correct format. Upon successful
     * validation, the booking details are added, and the form is cleared for
     * new input.
     *
     * @param evt The event triggered by the "Book Request" button click.
     */

    private void btnBookRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookRequestActionPerformed
        // TODO add your handling code here:

        String customerName = txtFldBookingName.getText();
        String customerPhone = txtFldBookingPhone.getText();
        String customerDate = txtFldBookingDate.getText();
        String customerTime = txtFldBookingTime.getText();
        String customerCourtNoText = txtFldBookingCourtNo.getText();

        if (customerName.isEmpty()) {
            lblBookingError.setText("Customer Name is required.");
        } else if (customerPhone.isEmpty()) {
            lblBookingError.setText("Customer Phone is required.");
        } else if (customerDate.isEmpty()) {
            lblBookingError.setText("Booking Date is required.");
        } else if (customerTime.isEmpty()) {
            lblBookingError.setText("Booking Time is required.");
        } else if (customerCourtNoText.isEmpty()) {
            lblBookingError.setText("Court Number is required.");
        } else if (!customerPhone.matches("\\d{10}")) {
            lblBookingError.setText("Invalid phone number. Must be 10 digits.");
        } else {
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

    /**
     * Handles the action when the "Delete Request" button is clicked.
     *
     * This method deletes the current booking request by invoking the
     * `deleteRequest` method. It removes the selected request from the system,
     * updating the displayed information accordingly.
     *
     * @param evt The event triggered by the "Delete Request" button click.
     */

    private void btnDeleteRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteRequestActionPerformed
        // TODO add your handling code here:
        deleteRequest();
    }//GEN-LAST:event_btnDeleteRequestActionPerformed

    /**
     * Handles the action when the "New Booking" button is clicked.
     *
     * This method validates the booking details entered by the user. It ensures
     * that all required fields are filled in, and that the phone number and
     * other numerical fields are valid. If the booking is valid, the new
     * booking is added to the system. If the booking already exists or there is
     * an error in the input, an appropriate error message is displayed.
     *
     * @param evt The event triggered by the "New Booking" button click.
     */

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
        } else if (customerName.isEmpty()) {
            lblAdminBookingError.setText("Customer Name is required.");
        } else if (customerPhone.isEmpty()) {
            lblAdminBookingError.setText("Customer Phone is required.");
        } else if (customerDate.isEmpty()) {
            lblAdminBookingError.setText("Booking Date is required.");
        } else if (customerTime.isEmpty()) {
            lblAdminBookingError.setText("Booking Time is required.");
        } else if (customerCourtNoText.isEmpty()) {
            lblAdminBookingError.setText("Court Number is required.");
        } else if (!customerPhone.matches("\\d{10}")) {
            lblAdminBookingError.setText("Invalid phone number. Must be 10 digits.");
        } else {
            int customerId = Integer.parseInt(customerIdText);
            boolean exists = false;
            for (CustomerModel existingBooking : bookingList) {
                if (existingBooking.getCustomerId() == customerId) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                lblAdminBookingError.setText("Booking with this ID already exists.");
            } else {
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

    /**
     * Handles the action when the "Update Booking" button is clicked.
     *
     * This method validates the booking details entered by the user. It ensures
     * that all required fields are filled in, and that the phone number and
     * other numerical fields are valid. If the booking with the provided ID
     * exists, it updates the booking with the new details. If no such booking
     * exists, an error message is displayed. Appropriate error messages are
     * shown for invalid inputs as well.
     *
     * @param evt The event triggered by the "Update Booking" button click.
     */

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
        } else if (customerName.isEmpty()) {
            lblAdminBookingError.setText("Customer Name is required.");
        } else if (customerPhone.isEmpty()) {
            lblAdminBookingError.setText("Customer Phone is required.");
        } else if (customerDate.isEmpty()) {
            lblAdminBookingError.setText("Booking Date is required.");
        } else if (customerTime.isEmpty()) {
            lblAdminBookingError.setText("Booking Time is required.");
        } else if (customerCourtNoText.isEmpty()) {
            lblAdminBookingError.setText("Court Number is required.");
        } else if (!customerPhone.matches("\\d{10}")) {
            lblAdminBookingError.setText("Invalid phone number. Must be 10 digits.");
        } else {

            int customerId = Integer.parseInt(customerIdText);
            boolean exists = false;
            for (CustomerModel existingBooking : bookingList) {
                if (existingBooking.getCustomerId() == customerId) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                lblAdminBookingError.setText("Booking with this ID does not exist.");
            } else {
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

    /**
     * Handles the action when the "Delete Booking" button is clicked.
     *
     * This method checks if the customer ID field is filled and verifies if a
     * booking with the provided ID exists in the system. If the booking exists,
     * it deletes the booking and clears the input field. If no booking exists
     * with the provided ID, an error message is displayed. It also handles
     * invalid customer ID format and displays the appropriate error message.
     *
     * @param evt The event triggered by the "Delete Booking" button click.
     */

    private void btnDeleteBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBookingActionPerformed
        // TODO add your handling code here:
        String customerIdText = txtFldCustomerId.getText();

        if (customerIdText.isEmpty()) {
            lblAdminBookingError.setText("Customer ID is required.");
        } else {
            int customerId = Integer.parseInt(customerIdText);
            boolean exists = false;
            for (CustomerModel existingBooking : bookingList) {
                if (existingBooking.getCustomerId() == customerId) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                lblAdminBookingError.setText("Booking with this ID does not exist.");
            } else {
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

    /**
     * Handles the action when the "Return Home" button is clicked.
     *
     * This method loads the main screen of the application, effectively
     * returning the user to the home page or main view. It ensures that the
     * user can navigate back to the main screen after performing other actions.
     *
     * @param evt The event triggered by the "Return Home" button click.
     */

    private void btnReturnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnHomeActionPerformed
        // TODO add your handling code here:
        loadScreen("MainScreen");
    }//GEN-LAST:event_btnReturnHomeActionPerformed

    /**
     * Handles the action when the "Log Out" button is clicked.
     *
     * This method loads the main screen of the application, effectively logging
     * the user out by returning to the home page or main view. It ensures that
     * the user can exit the current session and navigate back to the main
     * screen.
     *
     * @param evt The event triggered by the "Log Out" button click.
     */

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // TODO add your handling code here:
        loadScreen("MainScreen");
    }//GEN-LAST:event_btnLogOutActionPerformed

    /**
     * Handles the mouse entered event for the "Home" label in the navigation
     * bar.
     *
     * This method changes the background color and text color of the "Home"
     * label when the mouse hovers over it, providing a visual indication of
     * interaction. The label's background color is set to a light red shade,
     * and the text color is set to white.
     *
     * @param evt The event triggered by the mouse entering the "Home" label
     * area.
     */

    private void lblNavbarHomelblMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNavbarHomelblMouseEntered
        // TODO add your handling code here:
        lblNavbarHomelbl.setBackground(new java.awt.Color(255, 151, 141));
        lblNavbarHomelbl.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_lblNavbarHomelblMouseEntered

    /**
     * Handles the mouse exited event for the "Home" label in the navigation
     * bar.
     *
     * This method reverts the background color and text color of the "Home"
     * label when the mouse exits the label area, returning it to its default
     * state. The label's background color is set to white, and the text color
     * is set to a dark gray.
     *
     * @param evt The event triggered by the mouse exiting the "Home" label
     * area.
     */

    private void lblNavbarHomelblMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNavbarHomelblMouseExited
        // TODO add your handling code here:
        lblNavbarHomelbl.setBackground(new java.awt.Color(255, 255, 255));
        lblNavbarHomelbl.setForeground(new java.awt.Color(102, 102, 102));
    }//GEN-LAST:event_lblNavbarHomelblMouseExited

    /**
     * Handles the mouse entered event for the "Booking" label in the navigation
     * bar.
     *
     * This method changes the background color and text color of the "Booking"
     * label when the mouse hovers over it, providing a visual cue for the user.
     * The background color is set to a light red, and the text color is set to
     * white.
     *
     * @param evt The event triggered by the mouse entering the "Booking" label
     * area.
     */

    private void lblNavbarBookingMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNavbarBookingMouseEntered
        // TODO add your handling code here:
        lblNavbarBooking.setBackground(new java.awt.Color(255, 151, 141));
        lblNavbarBooking.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_lblNavbarBookingMouseEntered

    /**
     * Handles the mouse exited event for the "Booking" label in the navigation
     * bar.
     *
     * This method reverts the background color and text color of the "Booking"
     * label when the mouse moves away from it. The background color is restored
     * to white, and the text color is set to a dark gray.
     *
     * @param evt The event triggered by the mouse exiting the "Booking" label
     * area.
     */

    private void lblNavbarBookingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNavbarBookingMouseExited
        // TODO add your handling code here:
        lblNavbarBooking.setBackground(new java.awt.Color(255, 255, 255));
        lblNavbarBooking.setForeground(new java.awt.Color(102, 102, 102));
    }//GEN-LAST:event_lblNavbarBookingMouseExited

    /**
     * Handles the mouse entered event for the "Announcement" label in the
     * navigation bar.
     *
     * This method changes the background color of the "Announcement" label to a
     * light red color and the text color to white when the mouse hovers over
     * it.
     *
     * @param evt The event triggered by the mouse entering the "Announcement"
     * label area.
     */

    private void lblNavbarAnnouncementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNavbarAnnouncementMouseEntered
        // TODO add your handling code here:
        lblNavbarAnnouncement.setBackground(new java.awt.Color(255, 151, 141));
        lblNavbarAnnouncement.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_lblNavbarAnnouncementMouseEntered

    /**
     * Handles the mouse exited event for the "Announcement" label in the
     * navigation bar.
     *
     * This method restores the background color of the "Announcement" label to
     * white and changes the text color to a dark gray when the mouse leaves the
     * label area.
     *
     * @param evt The event triggered by the mouse exiting the "Announcement"
     * label area.
     */

    private void lblNavbarAnnouncementMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNavbarAnnouncementMouseExited
        // TODO add your handling code here:
        lblNavbarAnnouncement.setBackground(new java.awt.Color(255, 255, 255));
        lblNavbarAnnouncement.setForeground(new java.awt.Color(102, 102, 102));
    }//GEN-LAST:event_lblNavbarAnnouncementMouseExited

    /**
     * Handles the action event triggered when the user selects an option from
     * the sorting combo box.
     *
     * This method sorts the booking list based on the selected criteria and
     * updates the table accordingly. It sorts the booking list by: - ID (in
     * ascending order) - Name (in ascending order) - Date (in descending order)
     *
     * After sorting, the table is cleared and the sorted data is added to the
     * table for display.
     *
     * @param evt The event triggered when the user selects an option from the
     * combo box.
     */

    private void cmbSortTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSortTableActionPerformed
        // TODO add your handling code here:
        Sort sort = new Sort();
        if (cmbSortTable.getSelectedIndex() == 1) {

            List<CustomerModel> sortedLists = sort.sortBookingById(bookingList, false);

            DefaultTableModel model = (DefaultTableModel) tblBookings.getModel();
            model.setRowCount(0);
            for (CustomerModel sortedList : sortedLists) {
                model.addRow(new Object[]{
                    sortedList.getCustomerId(), sortedList.getCustomerName(), sortedList.getCustomerPhone(), sortedList.getCustomerCourtNo(),
                    sortedList.getCustomerDate(), sortedList.getCustomerTime()
                });
            }
        } else if (cmbSortTable.getSelectedIndex() == 2) {
            List<CustomerModel> sortedLists = sort.sortBookingByName(bookingList, false);

            DefaultTableModel model = (DefaultTableModel) tblBookings.getModel();
            model.setRowCount(0);
            for (CustomerModel sortedList : sortedLists) {
                model.addRow(new Object[]{
                    sortedList.getCustomerId(), sortedList.getCustomerName(), sortedList.getCustomerPhone(), sortedList.getCustomerCourtNo(),
                    sortedList.getCustomerDate(), sortedList.getCustomerTime()
                });
            }
        } else if (cmbSortTable.getSelectedIndex() == 3) {
            List<CustomerModel> sortedLists = sort.sortBookingByDate(bookingList, true);
            DefaultTableModel model = (DefaultTableModel) tblBookings.getModel();
            model.setRowCount(0);
            for (CustomerModel sortedList : sortedLists) {
                model.addRow(new Object[]{
                    sortedList.getCustomerId(), sortedList.getCustomerName(), sortedList.getCustomerPhone(), sortedList.getCustomerCourtNo(),
                    sortedList.getCustomerDate(), sortedList.getCustomerTime()
                });
            }
        }
    }//GEN-LAST:event_cmbSortTableActionPerformed

    /**
     * Handles the action event triggered when the user clicks on the search
     * icon.
     *
     * This method retrieves the search query entered by the user and searches
     * for the customer booking by name. If the booking list is empty or null,
     * an appropriate message is displayed. It then performs a search based on
     * the entered value using the `Search` class. If a matching booking is
     * found, the table is updated with the search result. If no result is
     * found, an error message is displayed.
     *
     * @param evt The event triggered when the search icon is clicked.
     */

    private void imgSearchIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgSearchIconMouseClicked
        // TODO add your handling code here:                                         
        String searchValue = txtFldSearchBooking.getText();
        if (bookingList == null || bookingList.isEmpty()) {
            lblSearchState.setText("No data to search.");
            return;
        }
        Sort sort = new Sort();

        Search search = new Search();
        CustomerModel searchedValue = search.searchByName(searchValue, sort.sortBookingByName(bookingList, false), 0, bookingList.size() - 1);

        if (searchedValue != null) {
            lblSearchState.setText("");
            DefaultTableModel model = (DefaultTableModel) tblBookings.getModel();
            model.setRowCount(0);
            model.addRow(new Object[]{
                searchedValue.getCustomerId(),
                searchedValue.getCustomerName(),
                searchedValue.getCustomerPhone(),
                searchedValue.getCustomerCourtNo(),
                searchedValue.getCustomerDate(),
                searchedValue.getCustomerTime()
            });
        } else {
            lblSearchState.setText("Searched Data does not exist.");
        }


    }//GEN-LAST:event_imgSearchIconMouseClicked

    private void txtFldSearchBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFldSearchBookingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFldSearchBookingActionPerformed

    /**
     * Handles the action event triggered when the Hero Button is clicked.
     *
     * This method removes all components from the main panel and adds the
     * booking page panel (`pnlBookingPage`). It then repaints and revalidates
     * the main panel to display the updated content.
     *
     * @param evt The event triggered when the Hero Button is clicked.
     */

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
    private javax.swing.JComboBox<String> cmbSortTable;
    private javax.swing.JDialog diaBookingDialog;
    private javax.swing.JLabel imgAdminLogo;
    private javax.swing.JLabel imgBookingCourtImage1;
    private javax.swing.JLabel imgCourtImage;
    private javax.swing.JLabel imgFitnessCenter;
    private javax.swing.JLabel imgFoodCourt;
    private javax.swing.JLabel imgHeroImg;
    private javax.swing.JLabel imgLoginLog;
    private javax.swing.JLabel imgLogo;
    private javax.swing.JLabel imgSearchIcon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAdminBookingError;
    private javax.swing.JLabel lblAdminDashBoardTitle;
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
    private javax.swing.JLabel lblNoticeList;
    private javax.swing.JLabel lblNoticeTitle;
    private javax.swing.JLabel lblRequestCounter;
    private javax.swing.JLabel lblSearchState;
    private javax.swing.JLabel lblServicesFood;
    private javax.swing.JLabel lblServicesOtherActivities;
    private javax.swing.JLabel lblServicesTitle;
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
    private javax.swing.JPanel pnlBookingForm;
    private javax.swing.JPanel pnlBookingMain;
    private javax.swing.JPanel pnlBookingPage;
    private javax.swing.JPanel pnlBookingRequest;
    private javax.swing.JPanel pnlBookingRequestTableBooking;
    private javax.swing.JPanel pnlCardBooking;
    private javax.swing.JPanel pnlCardDashboard;
    private javax.swing.JPanel pnlCardNotice;
    private javax.swing.JPanel pnlDashboardBooking;
    private javax.swing.JPanel pnlDashboardNewBooking;
    private javax.swing.JPanel pnlHomePage;
    private javax.swing.JPanel pnlHomePagePanel;
    private javax.swing.JPanel pnlLoginMain;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMainCard;
    private javax.swing.JPanel pnlMainHero;
    private javax.swing.JPanel pnlMainNavbar;
    private javax.swing.JPanel pnlMainScreen;
    private javax.swing.JPanel pnlNoticeBoard;
    private javax.swing.JPanel pnlNoticeForm;
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
    private javax.swing.JTextArea txtAreaServicesCourt;
    private javax.swing.JTextArea txtAreaServicesFood;
    private javax.swing.JTextArea txtAreaServicesFood1;
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
    private javax.swing.JTextField txtFldSearchBooking;
    private javax.swing.JTextField txtFldTime;
    private javax.swing.JTextField txtFldUsername;
    // End of variables declaration//GEN-END:variables
}
