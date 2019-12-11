package com.jjb.ecms.biz.activiti;

import java.util.List;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

/**
 * @author BIG.2Spring
 *
 */
public class ClassDelegateExt extends ClassDelegate {

	/**
	 * @param className
	 * @param fieldDeclarations
	 */
	public ClassDelegateExt(String className,
			List<FieldDeclaration> fieldDeclarations) {
		super(className, fieldDeclarations);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	protected ActivityBehavior getActivityBehaviorInstance(
			ActivityExecution execution) {
		Object delegateInstance = instantiateDelegate(className,
				fieldDeclarations);

		if (delegateInstance instanceof ActivityBehavior) {
			return determineBehaviour((ActivityBehavior) delegateInstance,
					execution);
		} else if (delegateInstance instanceof JavaDelegate) {
			return determineBehaviour(
					new ServiceTaskJavaDelegateActivityBehaviorExt(
							(JavaDelegate) delegateInstance), execution);
		} else {
			throw new ActivitiIllegalArgumentException(delegateInstance
					.getClass().getName()
					+ " doesn't implement "
					+ JavaDelegate.class.getName()
					+ " nor "
					+ ActivityBehavior.class.getName());
		}
	}

	protected ExecutionListener getExecutionListenerInstance() {
		Object delegateInstance = instantiateDelegate(className,
				fieldDeclarations);
		if (delegateInstance instanceof ExecutionListener) {
			return (ExecutionListener) delegateInstance;
		} else if (delegateInstance instanceof JavaDelegate) {
			return new ServiceTaskJavaDelegateActivityBehaviorExt(
					(JavaDelegate) delegateInstance);
		} else {
			throw new ActivitiIllegalArgumentException(delegateInstance
					.getClass().getName()
					+ " doesn't implement "
					+ ExecutionListener.class + " nor " + JavaDelegate.class);
		}
	}

}
