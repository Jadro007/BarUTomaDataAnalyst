package cz.muni.fi.PB138.main.gui;

import cz.muni.fi.PB138.main.entities.Bar;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Class used for model of table of bars.
 * Created by Eva on 30.5.2015.
 */
public class BarsTableModel extends AbstractTableModel {

    //list of all bars
    private static List<Bar> barList = LoadManager.getLoadAdmin().getAdminsBars();
    //list of selected bars (items with same index = bar <-> is selected)
    private static List<Boolean> selectionList = initSelectionList();

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
                return selectionList.get(row);
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
        selectionList.set(row, (Boolean) value);
        fireTableCellUpdated(row, column);
    }

    /**
     * Returns list of bars with true selection value.
     * @return List<Bar> of selected bars in table by user
     */
    public List<Bar> getSelectedBars() {
        List<Bar> selectedBars = new ArrayList<>();
        for (int i = 0; i < selectionList.size(); i++) {
            if (selectionList.get(i)) {
                selectedBars.add(this.barList.get(i));
            }
        }
        return Collections.unmodifiableList(selectedBars);
    }

    /**
     * Auxiliary method for initialisation of selection list.
     * @return List<Boolean> of false Booleans with length of bar list
     */
    private static List<Boolean> initSelectionList() {
        List<Boolean> checkList = new ArrayList<>();
        for (int i = 0; i < barList.size(); i++) {
            checkList.add(Boolean.FALSE);
        }
        return checkList;
    }
}