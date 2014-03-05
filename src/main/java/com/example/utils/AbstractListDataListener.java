package com.example.utils;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * @author innokenty
 */
public abstract class AbstractListDataListener implements ListDataListener {

    @Override
    public void intervalAdded(ListDataEvent e) {
    }

    @Override
    public void intervalRemoved(ListDataEvent e) {
    }

    @Override
    public void contentsChanged(ListDataEvent e) {
    }
}
