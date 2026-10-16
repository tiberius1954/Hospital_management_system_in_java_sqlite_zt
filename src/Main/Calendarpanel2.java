package Main;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import Classes.Calhelper;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import Classes.Hhelper.SimpleHeaderRenderer;

public class Calendarpanel2 extends JPanel {
	private JTable calendarTable, timeTable;
	private DefaultTableModel tableModel, timeModel;
	private JButton prevButton, nextButton;
	private YearMonth currentYearMonth;
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	ResultSet rs;
	Connection con = null;
	JPanel basePanel;
	JScrollPane scrollPane = new JScrollPane();
	String selectedDate;
   ThickBorderCellRenderer renderer;
	Border headerBorder;
	JLabel lbcaldate = hh.clabel("");
	JLabel lbtimess = new JLabel("");
	JLabel lbavailable, lbunavailable, lbunava, lbava;
	DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
	DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
	DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	String monthYear;
	static String monthYearzt;
	String[] daysOfWeek = cc.daynames();
	String mydocid = "";
	String myposid = "";
	String mydepid ="";
	static ArrayList<ArrayList<String>> Arr2d = new ArrayList<ArrayList<String>>();
	DefaultListModel<String> listModel = new DefaultListModel<>();
	JList<String> timelist = new JList<>(listModel);
	private JFrame pframe;


	public Calendarpanel2(String pdocid, String pposid, String pdepid, JFrame parent) {
		mydocid = pdocid;
		myposid = pposid;
		mydepid = pdepid;
		pframe = parent;

		this.add(createGUI());

	}

	public Calendarpanel2() {

		// this.add(createGUI());

	}

	protected JPanel createGUI() {
		basePanel = new JPanel(null);
		basePanel.setBounds(1, 1, 710, 495);
		basePanel.setBackground(new Color(178, 216, 216));
		basePanel.setForeground(Color.BLACK);

		prevButton = hh.cbutton("Previous month");
		prevButton.setBackground(hh.lpiros);
		prevButton.setBounds(1, 3, 140, 30);
		prevButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentYearMonth = currentYearMonth.minusMonths(1);
				updateCalendar();
			}
		});

		nextButton = hh.cbutton("Next month");
		nextButton.setBackground(hh.lpiros);
		nextButton.setBounds(407, 3, 140, 30);
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentYearMonth = currentYearMonth.plusMonths(1);
				updateCalendar();
			}
		});
		lbcaldate.setBounds(207, 3, 100, 30);

		JPanel navPanel = new JPanel(null);
		navPanel.setBounds(10, 10, 645, 40);
		navPanel.setBackground(new Color(178, 216, 216));

		navPanel.add(prevButton);
		navPanel.add(lbcaldate);
		navPanel.add(nextButton);
		basePanel.add(navPanel);

		tableModel = new DefaultTableModel(null, daysOfWeek);
		calendarTable = new JTable(tableModel) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		calendarTable.setBorder(hh.bb);
		JTableHeader header = calendarTable.getTableHeader();
		header.setDefaultRenderer(hh.new SimpleHeaderRenderer());
		header.setBackground(hh.sotetkek);
		header.setForeground(Color.white);
		calendarTable.setFont(new Font("Consolas", Font.BOLD, 16));
		renderer = new ThickBorderCellRenderer(1, 1);	
		currentYearMonth = YearMonth.now();
		calendarTable.setRowHeight(60);
		calendarTable.setBackground(hh.vvgray);
		calendarTable.setSelectionBackground(Color.YELLOW);
		calendarTable.getTableHeader().setReorderingAllowed(false);
		calendarTable.getTableHeader().setResizingAllowed(false);
		calendarTable.setCellSelectionEnabled(true);
		calendarTable.setColumnSelectionAllowed(true);
		calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(calendarTable);
		scrollPane.setBounds(10, 50, 550, 394);
		scrollPane.setBorder(hh.bb);
		basePanel.add(scrollPane);
		scrollPane.setColumnHeader(new JViewport() {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 32;
				return d;
			}
		});
	
		calendarTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = calendarTable.rowAtPoint(e.getPoint());
				int col = calendarTable.columnAtPoint(e.getPoint());
				// calendarTable.changeSelection(row, col, false,false);
				// calendarTable.requestFocus();
				selectedDate = getSelectedDate();
				if (selectedDate != null) {
					ArrayList <String>list = arr2dSearch(selectedDate);
					listModel.clear();
					timelist.setModel(listModel);
					for (String item : list) {
					    listModel.addElement(item);
					}							
				} 
			}
		});

		lbtimess.setText("Free times");
		lbtimess.setBorder(hh.bb);
		lbtimess.setBackground(hh.sotetkek);
		lbtimess.setForeground(Color.white);
		lbtimess.setFont(new Font("Consolas", Font.BOLD, 14));
		lbtimess.setOpaque(true);
		lbtimess.setBounds(575	, 50, 120, 32);
		lbtimess.setHorizontalAlignment(JLabel.CENTER);
		basePanel.add(lbtimess);

		timelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		timelist.setLayoutOrientation(JList.VERTICAL);
		timelist.setFont(new Font("tahoma", 1, 16));
		timelist.setBorder(hh.bb);
		timelist.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					JList source = (JList) e.getSource();
					int selectedIndex = source.getSelectedIndex();
					selectedDate = getSelectedDate();
					String stime = timelist.getSelectedValue().toString();
					
					if (! hh.zempty(selectedDate) && !hh.zempty(stime)) {
				         	((Weekpanel2) pframe).writeout(selectedDate, stime);
					}			
				}
			}
		});
		
		timelist.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                // Add a bottom border (line) between items
                label.setBorder(new MatteBorder(0, 0, 2, 0, Color.GRAY));
                label.setHorizontalAlignment(SwingConstants.CENTER);

                return label;
            }
        });
		
		JScrollPane scrollTimePane = new JScrollPane(timelist, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollTimePane.setBounds(575, 83, 120, 360);
		scrollTimePane.setBorder(hh.bb);
		basePanel.add(scrollTimePane);
		
		
		lbavailable = hh.llabel("Available");
		lbavailable.setBounds(20, 460, 80, 25);
		basePanel.add(lbavailable);		
		lbava= hh.llabel(" ");
		lbava.setBounds(110, 465, 15, 15);
		lbava.setOpaque(true);
		lbava.setBackground(Color.WHITE);
		lbava.setBorder(hh.bb);
		basePanel.add(lbava);
		
		lbunavailable = hh.llabel("Unavailable");
		lbunavailable.setBounds(130, 460, 100, 25);
		basePanel.add(lbunavailable);		
		lbunava= hh.llabel(" ");
		lbunava.setBounds(240, 465, 15, 15);
		lbunava.setOpaque(true);
		lbunava.setBackground(Color.LIGHT_GRAY);
		lbunava.setBorder(hh.bb);
		basePanel.add(lbunava);		
	
		updateCalendar();
		return basePanel;
	}

	private void updateCalendar() {
		tableModel.setRowCount(0);
		tableModel.setColumnCount(7);
		listModel.clear();
		timelist.setModel(listModel);

		LocalDate firstDayOfMonth = currentYearMonth.atDay(1);
		int daysInMonth = currentYearMonth.lengthOfMonth();
		int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
		int row = 0;
		int column = dayOfWeek - 1;

		for (int i = 1; i <= daysInMonth; i++) {
			if (row == tableModel.getRowCount()) {
				tableModel.addRow(new Object[7]);
			}
			LocalDate date = firstDayOfMonth.plusDays(i - 1);	
			String cellValue = date.format(dayFormatter);
			tableModel.setValueAt(cellValue, row, column);
			column = (column + 1) % 7;
			if (column == 0) {
				row++;
			}
		}
		monthYear = currentYearMonth.format(yearFormatter) + " - " + currentYearMonth.format(monthFormatter);
		monthYearzt = currentYearMonth.format(yearFormatter) + "-" + currentYearMonth.format(monthFormatter);
		lbcaldate.setText(monthYear);

		for (int i = 0; i < calendarTable.getColumnCount(); i++) {
			calendarTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		Arr2d.clear();
		datacollect();
	    datacollect1();
	    datacollect2();
		calendarTable.repaint();
	}

	private String getSelectedDate() {
		int selectedRow = calendarTable.getSelectedRow();
		int selectedColumn = calendarTable.getSelectedColumn();// - 1;
		if (selectedRow != -1 && selectedColumn != -1) {
			String val= null;
			try {
				Object ovalue =  calendarTable.getValueAt(selectedRow, selectedColumn);
				if ( ovalue!=null) {
		     	val = calendarTable.getValueAt(selectedRow, selectedColumn).toString();
				}
			} catch (Exception e) {
				System.out.println("null hiba");
			}
			if (!hh.zempty(val))
				return currentYearMonth.format(yearFormatter) + "-" + currentYearMonth.format(monthFormatter) + '-'
						+ calendarTable.getValueAt(selectedRow, selectedColumn).toString();
		}
		return null;
	}


	static class ThickBorderCellRenderer extends DefaultTableCellRenderer {
		private final int targetRow;
		private final int targetCol;
		private final Border normalBorder;

		public ThickBorderCellRenderer(int targetRow, int targetCol) {
			this.targetRow = targetRow;
			this.targetCol = targetCol;
			this.normalBorder = new MatteBorder(1, 1, 1, 1, Color.BLACK); // normal border
		}	

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			((JComponent) c).setBorder(normalBorder);
			setHorizontalAlignment(JLabel.CENTER);
			try{
		    Object vv = table.getModel().getValueAt(row, column);
		    if ( vv != null) {
			String day = vv.toString();
			String mydate = monthYearzt  + "-" +day;	
		     int num = dateseeker(mydate);
		    if (num == 1) {
          		c.setBackground(Color.WHITE);
	     	}else {	     	
	     		c.setBackground(Color.LIGHT_GRAY);
	        }
		    }
			} catch (Exception e) {
				e.printStackTrace();
			} 			
			
			if (isSelected == true) {
				c.setBackground(Color.YELLOW);
			}
			return c;
		}
	}
	
	private static int dateseeker(String date) {		
		int ret =0;
		for (int ii = 0; ii < Arr2d.size(); ii++) {
			String sdate = (String) Arr2d.get(ii).get(0);		
			if (sdate.contains(date)) {	
			    ret = 1;
			    break;
			}
		}			
		return ret;
	}

	private void datacollect() {
		String sql = "";
		for (int i = 0; i < 7; i++) {
			String dayname = daysOfWeek[i];
			sql = "select starttime, endtime from doctorapptimes  where empid ='" + mydocid 
					+ "' and posid ='" + myposid + "' and depid= '" + mydepid
					+ "' and dayname ='" + dayname + "'";

			try {
				rs = dh.GetData(sql);
				while (rs.next()) {
					String stime = rs.getString("starttime").trim();
					String etime = rs.getString("endtime").trim();
					doctime(stime, etime, dayname);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dh.CloseConnection();
			}
		}	
	}
	private void datacollect1() {
		ArrayList<ArrayList<String>> Arr2dx = new ArrayList<ArrayList<String>>();
		String sdate;
		String stime;
		String  sql;
		Boolean found;
		for (int ii = 0; ii < Arr2d.size(); ii++) {
			 sdate = (String) Arr2d.get(ii).get(0);				
			stime = (String) Arr2d.get(ii).get(1);
			
			sql = "select count(*) from appointments where app_date = '" + sdate 
					+"' and  app_time='" +stime +"' and docid ='" + mydocid  
					+ "' and posid = '" + myposid +"' and depid='" +mydepid +"'";
				found=	 dd.recordfound(sql);
				if (found == true) {
					ArrayList row = new ArrayList();
					row.add(sdate);
					row.add(stime);
					 Arr2dx.add(row);		
				}
		}
		for (int ii = 0; ii < Arr2dx.size(); ii++) {
    		String ssdate = (String) Arr2dx.get(ii).get(0);				
    		String sstime = (String) Arr2dx.get(ii).get(1);
    		itemremove(ssdate, sstime);
	      }		
		}
	
	private void datacollect2() {	
		ArrayList<ArrayList<String>> Arr2dx = new ArrayList<ArrayList<String>>();
		String  sql;
		String sdate;
		String searchtime;
		Boolean found;
		for (int ii = 0; ii < Arr2d.size(); ii++) {			
			 sdate = (String) Arr2d.get(ii).get(0);				
			searchtime = (String) Arr2d.get(ii).get(1);		  
			sql = "select startdate, enddate, starttime, endtime from docappticancel  where "
					+ "startdate <= date('" + sdate + "')" +
			       " and enddate >= date('"+sdate +"') and  empid ='" + mydocid 
			       + "' and posid = '" + myposid +"' and depid='" + mydepid +"'";
			try {
				rs = dh.GetData(sql);
				while (rs.next()) {
					String starttime = rs.getString("starttime").trim();
					String endtime = rs.getString("endtime").trim();	
					found = filter(starttime, endtime,searchtime);
					if (found) {	
						ArrayList row = new ArrayList();
						row.add(sdate);
						row.add(searchtime);
						 Arr2dx.add(row);						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dh.CloseConnection();
			}
		}
		
		for (int ii = 0; ii < Arr2dx.size(); ii++) {
    		String ssdate = (String) Arr2dx.get(ii).get(0);				
    		String stime = (String) Arr2dx.get(ii).get(1);
    		itemremove(ssdate, stime);
	}		
		
//		for (int ii = 0; ii < Arr2d.size(); ii++) {
//			String nap = (String) Arr2d.get(ii).get(0);				
//			String ido = (String) Arr2d.get(ii).get(1);
//			System.out.println(nap + "  " + ido);
//		}
	}
	
	private Boolean filter(String starttime, String endtime, String searchtime) {
		Boolean found = false;
		LocalTime sTime = LocalTime.parse(starttime);
		LocalTime eTime = LocalTime.parse(endtime);	
		LocalTime Target = LocalTime.parse(searchtime);
			if (Target.compareTo(sTime) >= 0 && Target.compareTo(eTime) < 0) {
		     found= true;
		}
		return found;
	}	

	private void doctime(String stime, String etime, String dayname) {
		String svalue;
		List<String> daytimes = new ArrayList<String>();
		LocalTime dstime = hh.stoloctime(stime);
		LocalTime detime = hh.stoloctime(etime);
		detime = detime.minusMinutes(15);
		LocalTime xtime = dstime;
		svalue = xtime.format(timeFormatter);
		daytimes.add(svalue);
		while (xtime.isBefore(detime)) {
			xtime = xtime.plusMinutes(15);
			svalue = xtime.format(timeFormatter);
			daytimes.add(svalue);
		}
		columndaystimes(daytimes, dayname);
       }

	private void columndaystimes(List<String> times, String dayname) {
		int col = hh.tombekeres(daysOfWeek, dayname);
		for (int i = 0; i < calendarTable.getRowCount(); i++) {
			Object ovalue = calendarTable.getValueAt(i, col);
			if (ovalue !=null) {
			String day = calendarTable.getValueAt(i, col).toString();
			if (!hh.zempty(day)) {
				String sdate = monthYearzt + "-" + day;
				for (int j = 0; j < times.size(); j++) {
					ArrayList row = new ArrayList();
					row.add(sdate);
					row.add(times.get(j));
					Arr2d.add(row);
				}
			}
			}
		}
	}

	private  ArrayList <String> arr2dSearch(String search) {
		ArrayList<String> matches = new ArrayList<String>();
		for (int ii = 0; ii < Arr2d.size(); ii++) {
			String sdate = (String) Arr2d.get(ii).get(0);
			String stime = (String) Arr2d.get(ii).get(1);
			if (sdate.contains(search)) {	
				matches.add(stime);
			}
		}	
		return matches;
	}
	
	private void itemremove(String idate, String itime) {
  		for (int ii = 0; ii < Arr2d.size(); ii++) {
  			String sdate = (String) Arr2d.get(ii).get(0);
  			String stime = (String) Arr2d.get(ii).get(1);
  			if (sdate.equals(idate) && stime.equals(itime)) {	
  		//	   Arr2d.get(ii).remove(ii);		
  				 Arr2d.remove(ii);
  			   break;
  			}		
  		}	
  	}
	}

