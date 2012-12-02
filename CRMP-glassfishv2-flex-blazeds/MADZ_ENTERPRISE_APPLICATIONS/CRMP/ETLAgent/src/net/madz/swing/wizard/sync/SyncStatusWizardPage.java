package net.madz.swing.wizard.sync;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.madz.etl.engine.ISyncListener;
import net.madz.etl.engine.SyncEngine;
import net.madz.etl.engine.SyncException;
import net.madz.swing.view.util.GraphicFactory;
import net.madz.swing.wizard.JWizardPage;
import net.madz.swing.wizard.WizardMessage;
import net.madz.swing.wizard.displayable.IMixingPlantDisplayable;
import net.madz.swing.wizard.model.PlantsTableModel;

public class SyncStatusWizardPage extends JWizardPage implements ISyncListener {

	private static final String INDENT = "  ";
	private static final long serialVersionUID = 4742451513402732660L;
	private static final String MESSAGE_TASK_STARTED = WizardMessage.MESSAGE_TASK_STARTED;
	protected static final String MESSAGE_TASK_COMPLETED = WizardMessage.MESSAGE_TASK_COMPLETED;
	protected static final String MESSAGE_TASK_FAILED = "Sync Failed with following exception:";
	protected static final String MEASSAGE_TABLE_NAME = WizardMessage.MEASSAGE_TABLE_NAME;
	protected static final String MESSAGE_IMPORT_ENDED = WizardMessage.MESSAGE_IMPORT_ENDED;
	protected static final String MESSAGE_IMPORT_STARTED = WizardMessage.MESSAGE_IMPORT_STARTED;;
	protected static final String MESSAGE_DATA_SYNC_STARTED = WizardMessage.MESSAGE_DATA_SYNC_STARTED;;
	protected static final String MESSAGE_DATA_SYNC_ENDED = WizardMessage.MESSAGE_DATA_SYNC_ENDED;;
	protected static final String MESSAGE_BATCH = WizardMessage.MESSAGE_BATCH;;
	protected static final String MESSAGE_START_IMPORTING = WizardMessage.MESSAGE_START_IMPORTING;;
	protected static final String MESSAGE_END_IMPORTING = WizardMessage.MESSAGE_END_IMPORTING;;
	protected static final String MESSAGE_COMPLETED_QTY = WizardMessage.MESSAGE_COMPLETED_QTY;;
	protected static final String MESSAGE_TIME_COST = WizardMessage.MESSAGE_TIME_COST;;
	protected static final String MESSAGE_START_SYNCING = WizardMessage.MESSAGE_START_SYNCING;
	protected static final String MESSAGE_IMPORT_FAILED = WizardMessage.MESSAGE_IMPORT_FAILED;
	protected static final String MESSAGE_SYNC_FAILED = WizardMessage.MESSAGE_SYNC_FAILED;
	protected static final String MESSAGE_IMPORT_BATCH_FAILED = WizardMessage.MESSAGE_IMPORT_BATCH_FAILED;
	protected static final String MESSAGE_FOLLOWING_ERROR = WizardMessage.MESSAGE_FOLLOWING_ERROR;
	protected static final String MESSAGE_SYNC_BATCH_FAILED = WizardMessage.MESSAGE_SYNC_BATCH_FAILED;
	protected static final String MESSAGE_MILLIS_UNIT = WizardMessage.MESSAGE_MILLIS_UNIT;
	protected static final String MESSAGE_START_DATA_SYNCING = WizardMessage.MESSAGE_START_DATA_SYNCING;
	protected static final String MESSAGE_END_DATA_SYNCING = WizardMessage.MESSAGE_END_DATA_SYNCING;

	private JTextArea statusTextArea;

	public SyncStatusWizardPage(SyncProductionRecordWizard wizard) {
		super();
		String title = WizardMessage.SYNC_STATUS;
		this.setStepTitle(title);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				validatePage();
			}
		});
		addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				ProductionRecordSyncWizardContext.getInstance()
						.reloadStatusTableModel();
				final PlantsTableModel plantTableModel = ProductionRecordSyncWizardContext
						.getInstance().getPlantTableModel();
				final List<IMixingPlantDisplayable> plants = plantTableModel
						.getPlants();
				final ArrayList<String> plantIds = new ArrayList<String>();

				for (IMixingPlantDisplayable plant : plants) {
					if (plant.isSelected()) {
						plantIds.add(plant.getPlantId());
					}
				}

				SyncEngine.getInstance().setSyncListener(
						SyncStatusWizardPage.this);

				new Thread(new Runnable() {

					@Override
					public void run() {
						SyncEngine.getInstance().doSync(
								plantIds.toArray(new String[0]));

					}
				}).start();
			}

			@Override
			public void componentResized(ComponentEvent e) {
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}

		});
	}

	@Override
	public void createPartControl() {
		final JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		final JScrollPane tableScrollPane = createStatusTablePanel();
		centerPanel.add(tableScrollPane, BorderLayout.NORTH);

		final JScrollPane logScrollPane = createLogScrollPanel();
		centerPanel.add(logScrollPane, BorderLayout.SOUTH);

		getContentPane().add(centerPanel, BorderLayout.CENTER);
	}

	private JScrollPane createLogScrollPanel() {
		statusTextArea = new JTextArea();
		statusTextArea.setLineWrap(true);

		statusTextArea.setText(WizardMessage.SYNC_DETAIL_INFORMATION);

		final JScrollPane scrollPane = new JScrollPane(statusTextArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(500, 150));
		return scrollPane;
	}

	private JScrollPane createStatusTablePanel() {

		final JTable plantsTable = GraphicFactory
				.createTable(ProductionRecordSyncWizardContext.getInstance()
						.getSyncStatusTableModel());
		plantsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		plantsTable.getTableHeader().setReorderingAllowed(false);
		plantsTable.setVisible(true);

		final TableColumnModel columnModel = plantsTable.getColumnModel();
		final TableColumn checkBoxColumn = columnModel.getColumn(0);
		checkBoxColumn.setMaxWidth(40);

		ProductionRecordSyncWizardContext.getInstance()
				.getSyncStatusTableModel()
				.addTableModelListener(new TableModelListener() {

					@Override
					public void tableChanged(TableModelEvent e) {
						validatePage();
					}
				});

		final TableColumn nameColumn = columnModel.getColumn(1);
		nameColumn.setMaxWidth(80);
		nameColumn.setMinWidth(60);

		final TableColumn importQuantityColumn = columnModel.getColumn(2);
		importQuantityColumn.setMaxWidth(60);
		importQuantityColumn.setMinWidth(30);

		final TableColumn importIndicatorColumn = columnModel.getColumn(3);
		importIndicatorColumn.setMaxWidth(200);
		importIndicatorColumn.setMinWidth(100);

		final TableColumn syncQuantityColumn = columnModel.getColumn(4);
		syncQuantityColumn.setMaxWidth(60);
		syncQuantityColumn.setMinWidth(30);

		final TableColumn syncIndicatorColumn = columnModel.getColumn(5);
		syncIndicatorColumn.setMaxWidth(200);
		syncIndicatorColumn.setMinWidth(100);

		plantsTable.setCellSelectionEnabled(true);
		// plantsTable.setEditingColumn(0);
		plantsTable.setShowGrid(true);

		JScrollPane scrollPane = new JScrollPane(plantsTable,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		plantsTable.setMaximumSize(new Dimension(500, 150));
		plantsTable.setPreferredScrollableViewportSize(new Dimension(500, 150));

		plantsTable.setShowGrid(true);
		scrollPane.setWheelScrollingEnabled(true);

		return scrollPane;
	}

	protected void validatePage() {
		// setErrorInfo(null);
	}

	@Override
	public void onTaskStarted() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				StringBuilder statusText = new StringBuilder(statusTextArea
						.getText());
				statusText.append("\n");
				statusText.append(MESSAGE_TASK_STARTED);
				statusTextArea.setText(statusText.toString());
			}
		});
	}

	@Override
	public void onTaskCompleted() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				StringBuilder statusText = new StringBuilder(statusTextArea
						.getText());
				statusText.append("\n");
				statusText.append(MESSAGE_TASK_COMPLETED);
				statusTextArea.setText(statusText.toString());
			}
		});

	}

	@Override
	public void onTaskFailed(final SyncException ex) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				StringBuilder statusText = new StringBuilder(statusTextArea
						.getText());
				statusText.append("\n");
				statusText.append(MESSAGE_TASK_FAILED);
				statusText.append("\n");
				statusText.append(getErrorMessage(ex));
				statusTextArea.setText(statusText.toString());
			}
		});
	}

	@Override
	public void onImportStarted(final SyncContext context) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				final StringBuilder statusText = new StringBuilder(
						statusTextArea.getText());
				statusText.append("\n");
				statusText.append(INDENT);
				statusText.append(MESSAGE_IMPORT_STARTED);
				statusTextArea.setText(statusText.toString());

				ProductionRecordSyncWizardContext.getInstance().onStart(
						context.plantId);

			}
		});
	}

	@Override
	public void onImportEnded(SyncContext context) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				final StringBuilder statusText = new StringBuilder(
						statusTextArea.getText());
				statusText.append("\n");
				statusText.append(INDENT);
				statusText.append(MESSAGE_IMPORT_ENDED);
				statusTextArea.setText(statusText.toString());
			}
		});
	}

	@Override
	public void onImportBatchStarted(final SyncContext context) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				final StringBuilder statusText = new StringBuilder(
						statusTextArea.getText());
				statusText.append("\n");
				statusText.append(INDENT);
				statusText.append(INDENT);
				statusText.append(MEASSAGE_TABLE_NAME)
						.append(context.rawTableName)
						.append(MESSAGE_START_IMPORTING).append(",")
						.append(INDENT).append(MESSAGE_BATCH)
						.append(context.batchNumber);
				statusTextArea.setText(statusText.toString());
			}
		});
	}

	@Override
	public void onImportBatchEnded(final SyncContext context) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				final StringBuilder statusText = new StringBuilder(
						statusTextArea.getText());
				statusText.append("\n");
				statusText.append(INDENT);
				statusText.append(INDENT);
				statusText.append(MEASSAGE_TABLE_NAME)
						.append(context.rawTableName)
						.append(MESSAGE_END_IMPORTING).append(",")
						.append(INDENT).append(MESSAGE_BATCH)
						.append(context.batchNumber).append("\n")
						.append(INDENT).append(INDENT)
						.append(MESSAGE_COMPLETED_QTY)
						.append(context.completedQuantity).append("\n")
						.append(INDENT).append(INDENT)
						.append(MESSAGE_TIME_COST).append(context.timeCost)
						.append(MESSAGE_MILLIS_UNIT);
				ProductionRecordSyncWizardContext.getInstance().onStatusUpdate(
						context, true);
				statusTextArea.setText(statusText.toString());
			}
		});
	}

	@Override
	public void onSyncStarted(SyncContext context) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				final StringBuilder statusText = new StringBuilder(
						statusTextArea.getText());
				statusText.append("\n");
				statusText.append(INDENT);
				statusText.append(MESSAGE_DATA_SYNC_STARTED);
				statusTextArea.setText(statusText.toString());
			}
		});
	}

	@Override
	public void onSyncEnded(final SyncContext context) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				StringBuilder statusText = new StringBuilder(statusTextArea
						.getText());
				statusText.append("\n");
				statusText.append(INDENT);
				statusText.append(MESSAGE_DATA_SYNC_ENDED);
				statusTextArea.setText(statusText.toString());
				ProductionRecordSyncWizardContext.getInstance().onComplete(
						context.plantId);
			}
		});
	}

	@Override
	public void onSyncBatchStarted(final SyncContext context) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				final StringBuilder statusText = new StringBuilder(
						statusTextArea.getText());
				statusText.append("\n");
				statusText.append(INDENT);
				statusText.append(INDENT);
				statusText.append(MEASSAGE_TABLE_NAME)
						.append(context.rawTableName)
						.append(MESSAGE_START_DATA_SYNCING).append(",")
						.append(INDENT).append(MESSAGE_BATCH)
						.append(context.batchNumber);
				statusTextArea.setText(statusText.toString());
			}
		});
	}

	@Override
	public void onSyncBatchEnded(final SyncContext context) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				StringBuilder statusText = new StringBuilder(statusTextArea
						.getText());
				statusText.append("\n");
				statusText.append(INDENT);
				statusText.append(INDENT);
				statusText.append(MEASSAGE_TABLE_NAME)
						.append(context.rawTableName)
						.append(MESSAGE_END_DATA_SYNCING).append(",").append(INDENT)
						.append(MESSAGE_BATCH).append(context.batchNumber)
						.append("\n").append(INDENT).append(INDENT)
						.append(MESSAGE_COMPLETED_QTY)
						.append(context.completedQuantity).append("\n")
						.append(INDENT).append(INDENT)
						.append(MESSAGE_TIME_COST).append(context.timeCost)
						.append(MESSAGE_MILLIS_UNIT);
				statusTextArea.setText(statusText.toString());
				ProductionRecordSyncWizardContext.getInstance().onStatusUpdate(
						context, false);

			}
		});
	}

	@Override
	public void onImportFailed(final SyncContext context,
			final RuntimeException ex) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				StringBuilder statusText = new StringBuilder(statusTextArea
						.getText());
				statusText.append("\n").append(INDENT);
				statusText.append(MESSAGE_IMPORT_FAILED);
				statusText.append("\n").append(INDENT);
				statusText.append(getErrorMessage(ex));
				statusTextArea.setText(statusText.toString());
				ProductionRecordSyncWizardContext.getInstance().onFailed(
						context);
			}
		});
	}

	@Override
	public void onSyncFailed(final SyncContext context,
			final RuntimeException ex) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				StringBuilder statusText = new StringBuilder(statusTextArea
						.getText());
				statusText.append("\n").append(INDENT);
				statusText.append(MESSAGE_SYNC_FAILED);
				statusText.append("\n").append(INDENT);
				statusText.append(getErrorMessage(ex));
				statusTextArea.setText(statusText.toString());
				ProductionRecordSyncWizardContext.getInstance().onFailed(
						context);
			}
		});
	}

	@Override
	public void onImportBatchFailed(final SyncContext context,
			final RuntimeException ex) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				StringBuilder statusText = new StringBuilder(statusTextArea
						.getText());
				statusText.append("\n").append(INDENT).append(INDENT);
				statusText.append(MESSAGE_IMPORT_BATCH_FAILED);
				statusText.append("\n").append(INDENT).append(INDENT);
				statusText.append(context.batchNumber);
				statusText.append("\n").append(INDENT).append(INDENT);
				statusText.append(MESSAGE_FOLLOWING_ERROR);
				statusText.append(getErrorMessage(ex));
				statusTextArea.setText(statusText.toString());
				ProductionRecordSyncWizardContext.getInstance().onFailed(
						context);
			}
		});
	}

	@Override
	public void onSyncBatchFailed(final SyncContext context,
			final RuntimeException ex) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (null == statusTextArea) {
					return;
				}
				StringBuilder statusText = new StringBuilder(statusTextArea
						.getText());
				statusText.append("\n").append(INDENT).append(INDENT);
				statusText.append(MESSAGE_SYNC_BATCH_FAILED);
				statusText.append("\n").append(INDENT).append(INDENT);
				statusText.append(context.batchNumber);
				statusText.append("\n").append(INDENT).append(INDENT);
				statusText.append(MESSAGE_FOLLOWING_ERROR);

				statusText.append(getErrorMessage(ex));

				statusTextArea.setText(statusText.toString());
				ProductionRecordSyncWizardContext.getInstance().onFailed(
						context);

			}

		});
	}

	private String getErrorMessage(final Exception ex) {
		final StringBuilder sb = new StringBuilder();
		sb.append(ex.getMessage()).append("\n");
		StackTraceElement[] trace = ex.getStackTrace();
		for (int i = 0; i < trace.length; i++)
			sb.append("\tat " + trace[i]).append("\n");
		final String errorMessage = sb.toString();
		return errorMessage;
	}
}
