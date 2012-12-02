package net.madz.etl.engine;


public interface ISyncListener {

	public static enum SyncPhaseEnum {
		ImportStart, Importing, ImportEnd, SyncStart, Syncing, SyncEnd
	}

	public static class SyncContext {
		public String plantId;
		public String rawTableName;
		public String indicatorText;
		public int completedQuantity;
		public int batchNumber;
		public long timeCost;
		
		public SyncContext clone() {
			final SyncContext copy = new SyncContext();
			copy.plantId = plantId;
			copy.rawTableName = rawTableName;
			copy.indicatorText = indicatorText;
			copy.completedQuantity = completedQuantity;
			copy.batchNumber = batchNumber;
			copy.timeCost = timeCost;
			return copy;
		}
	}

	void onTaskStarted();

	void onTaskCompleted();

	void onTaskFailed(SyncException ex);

	void onImportStarted(SyncContext context);

	void onImportEnded(SyncContext context);

	void onImportBatchStarted(SyncContext context);

	void onImportBatchEnded(SyncContext context);

	void onSyncStarted(SyncContext context);

	void onSyncEnded(SyncContext context);

	void onSyncBatchStarted(SyncContext context);

	void onSyncBatchEnded(SyncContext context);

	void onImportFailed(SyncContext context, RuntimeException ex);

	void onSyncFailed(SyncContext context, RuntimeException ex);

	void onImportBatchFailed(SyncContext context, RuntimeException ex);

	void onSyncBatchFailed(SyncContext context, RuntimeException ex);
}
