package cz.muni.fi.PB138.main.gui;

import cz.muni.fi.PB138.main.entities.Bar;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eva on 30.5.2015.
 */
public class BarsTableModel extends AbstractTableModel {

    //TODO worker
    private static List<Bar> barList = LoadManager.getLoadAdmin().getAdminsBars();
    private static List<Boolean> checkList = initCheckList();

    @Override
    public int getRowCount() {
        return barList.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (row >= barList.size()) {
            return null;
        }
        switch (column) {
            case 0:
                return barList.get(row).getId();
            case 1:
                return barList.get(row).getName();
            case 2:
                return checkList.get(row);
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Bar";
            case 2:
                return "Select";
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return Long.class;
            case 1:
                return String.class;
            case 2:
                return Boolean.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return (col == 2);
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        //if (column !=  2 || row >= barList.size()) return;
        checkList.set(row, (Boolean) value);
        fireTableCellUpdated(row, column);
    }

    public List<Bar> getChckedBarList() {
        List<Bar> checkedBarList = new ArrayList<>();
        for (int i = 0; i < checkList.size(); i++) {
            if (checkList.get(i)) {
                checkedBarList.add(this.barList.get(i));
            }
        }
        return checkedBarList;
    }

    private static List<Boolean> initCheckList() {
        List<Boolean> checkList = new ArrayList<>();
        for (int i = 0; i < barList.size(); i++) {
            checkList.add(Boolean.FALSE);
        }
        return checkList;
    }
}
