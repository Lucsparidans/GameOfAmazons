package com.dke.game.Models.AI.OnlineEvolution;

import java.util.Comparator;

public class GenomeComparator  implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Genome && o2 instanceof Genome) {
            Genome g1 = (Genome) o1;
            Genome g2 = (Genome) o2;
            if(g1.getEval()>g2.getEval()){
                return 1;
            }
            else if(g2.getEval() > g1.getEval()){
                return -1;
            }
            else{
                return 0;
            }
        }
        return 0;
    }
}

