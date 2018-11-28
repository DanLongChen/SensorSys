package com.sensor.GATools;

import com.sensor.entity.Chromosome;

import java.util.Collections;
import java.util.List;

/**
 * Created by DanLongChen on 2018/11/26
 **/
public class GACopy {
    private List<Chromosome> beforeCopy;
    private List<Chromosome> afterCopy;

    public void doCopy() {
        for (Chromosome chromosome : beforeCopy) {
            afterCopy.add(chromosome.clone());
            afterCopy.add(chromosome.clone());
        }
        Collections.shuffle(afterCopy);
    }

    public List<Chromosome> getBeforeCopy() {
        return beforeCopy;
    }

    public void setBeforeCopy(List<Chromosome> beforeCopy) {
        this.beforeCopy = beforeCopy;
    }

    public List<Chromosome> getAfterCopy() {
        return afterCopy;
    }

    public void setAfterCopy(List<Chromosome> afterCopy) {
        this.afterCopy = afterCopy;
    }
}
