package net.madz.swing.wizard.sync;

import java.awt.Dimension;
import java.awt.HeadlessException;

import net.madz.swing.wizard.JWizard;
import net.madz.swing.wizard.WizardMessage;

public class SyncProductionRecordWizard extends JWizard {

	private static final long serialVersionUID = -7215051628635730132L;
	private SelectPlantsWizardPage selectionPage;
	private SyncStatusWizardPage statusPage;

	public SyncProductionRecordWizard() throws HeadlessException {
		super();
		String title = WizardMessage.SYNC_PRODUCTION_DATA_WIZARD;
		this.setTitle(title);
		this.setPreferredSize(new Dimension(700, 515));
	}

	@Override
	protected void createWizardPages() {
		selectionPage = new SelectPlantsWizardPage(this);
		statusPage = new SyncStatusWizardPage(this);
		addWizardPage(selectionPage);
		addWizardPage(statusPage);

		selectionPage.setBackStep(-1);
		selectionPage.setNextStep(1);

		statusPage.setBackStep(-1);
		statusPage.setNextStep(-1);
	}

	@Override
	protected void finish() {
		super.finish();
	}


}
