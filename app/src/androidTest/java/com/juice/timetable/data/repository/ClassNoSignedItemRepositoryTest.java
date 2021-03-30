package com.juice.timetable.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.data.bean.ClassNoSignedItem;

import org.junit.Test;

import java.util.List;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/24
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class ClassNoSignedItemRepositoryTest {

    @Test
    public void getClassNoSignedItem() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClassNoSignedItemRepositroy repository = new ClassNoSignedItemRepositroy(appContext);
        List<ClassNoSignedItem> classNoSignedItems = repository.getClassNoSignedItem();
        for (ClassNoSignedItem classNoSignedItem : classNoSignedItems) {
            Log.d("classNosignedItemTest", classNoSignedItem.toString());
        }
    }
}