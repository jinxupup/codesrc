package com.jjb.dmp.engine.kit;

import java.util.Comparator;

import com.jjb.dmp.engine.model.AbstractRuleVar;

public class RuleComparator implements Comparator<AbstractRuleVar> {

	@Override
	public int compare(AbstractRuleVar o1, AbstractRuleVar o2) {
	
		if(o1.getPriority() > o2.getPriority()){
			return 1;
		}else if(o1.getPriority() < o2.getPriority()){
			return -1;
		}
		
		return 0;
	}

}
