/*
 *  Copyright 2010 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package danfei.shulaibao.widget.wheel.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;

import danfei.shulaibao.widget.wheel.bean.City;


/**
 * The simple Array wheel adapter
 * @param <T> the element type
 */
public class CityWheelAdapter<T> extends AbstractWheelTextAdapter {

	 // items
    private List<City> mList;

    /**
     * Constructor
     * @param context the current context
     */
    public CityWheelAdapter(Context context, List<City> list) {
        super(context);
        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        this.mList = list;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < mList.size()) {
        	if(mList!=null && mList.get(index)!=null && !TextUtils.isEmpty(mList.get(index).getAreaName())){
        		String item = mList.get(index).getAreaName();
        		return item;
        	}
            /*if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return item.toString();*/
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return mList.size();
    }
    public CharSequence getItemAreaId(int index) {
        if (index >= 0 && index < mList.size()) {
        	if(mList!=null && mList.get(index)!=null && !TextUtils.isEmpty(mList.get(index).getAreaName())){
        		int item = mList.get(index).getAreaId();
        		return String.valueOf(item);
        	}
            /*if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return item.toString();*/
        }
        return null;
    }

}
