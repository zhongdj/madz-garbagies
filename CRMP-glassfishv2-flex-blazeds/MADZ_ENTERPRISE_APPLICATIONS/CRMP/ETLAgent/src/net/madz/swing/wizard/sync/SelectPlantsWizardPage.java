package net.madz.swing.wizard.sync;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.madz.swing.components.table.CheckBoxCellRender;
import net.madz.swing.components.table.PlantsTableCheckBoxCellEditor;
import net.madz.swing.view.util.GraphicFactory;
import net.madz.swing.wizard.JWizardPage;
import net.madz.swing.wizard.WizardMessage;

public class SelectPlantsWizardPage extends JWizardPage {

	private static final long serialVersionUID = 4742451513402732660L;

	public SelectPlantsWizardPage(SyncProductionRecordWizard wizard) {
		super();
		String title = WizardMessage.SELECT_PLANTS;
		this.setStepTitle(title);
	}

	public void processValidate() {
		Runnable validateJob = new Runnable() {
			public void run() {
				validatePage();
			}
		};
		SwingUtilities.invokeLater(validateJob);
	}

	@Override
	public void createPartControl() {

		final JScrollPane scrollPane = createPlantsTablePanel();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		// addComp(scrollPane, centerPanel, gbConstraints, 0, 0, 4, 4);
		processValidate();
		processValidate();
	}

	private JScrollPane createPlantsTablePanel() {

		final JTable plantsTable = GraphicFactory
				.createTable(ProductionRecordSyncWizardContext.getInstance()
						.getPlantTableModel());
		plantsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		plantsTable.getTableHeader().setReorderingAllowed(false);
		plantsTable.setVisible(true);

		final TableColumnModel columnModel = plantsTable.getColumnModel();
		final TableColumn checkBoxColumn = columnModel.getColumn(0);
		checkBoxColumn.setMaxWidth(30);

		ProductionRecordSyncWizardContext.getInstance().getPlantTableModel()
				.addTableModelListener(new TableModelListener() {

					@Override
					public void tableChanged(TableModelEvent e) {
						validatePage();
					}
				});

		final TableColumn nameColumn = columnModel.getColumn(1);
		nameColumn.setMaxWidth(150);
		nameColumn.setMinWidth(60);

		final TableColumn importIndicatorColumn = columnModel.getColumn(2);
		importIndicatorColumn.setMaxWidth(200);
		importIndicatorColumn.setMinWidth(100);

		final TableColumn syncIndicatorColumn = columnModel.getColumn(3);
		syncIndicatorColumn.setMaxWidth(200);
		syncIndicatorColumn.setMinWidth(100);

		// plantsTable.setCellSelectionEnabled(true);
		// plantsTable.setEditingColumn(0);
		plantsTable.setShowGrid(true);

		JScrollPane scrollPane = new JScrollPane(plantsTable,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		plantsTable.setMinimumSize(new Dimension(500, 450));
		plantsTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
		// plantsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		plantsTable.setShowGrid(true);
		scrollPane.setWheelScrollingEnabled(true);

		checkBoxColumn.setCellEditor(new PlantsTableCheckBoxCellEditor(
				ProductionRecordSyncWizardContext.getInstance()
						.getPlantTableModel()));
		checkBoxColumn.setCellRenderer(new CheckBoxCellRender());
		return scrollPane;
	}

	protected void validatePage() {
		if (null == ProductionRecordSyncWizardContext.getInstance()
				.getPlantTableModel()) {
			throw new IllegalStateException(
					"plantsModel should be initialized during createPartControl");
		}

		if (ProductionRecordSyncWizardContext.getInstance()
				.getPlantTableModel().hasSelectedPlants()) {
			setErrorInfo(null);
		} else {
			setErrorInfo(WizardMessage.ERROR_NO_PLANTS_SELECTED);
		}
	}

}
