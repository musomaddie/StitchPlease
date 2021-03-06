package project.comp5216.crossstitchorganiser;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "threads")
public class ThreadDatabaseItem {

	@PrimaryKey
	@NonNull
	@ColumnInfo(name="dmc")
    private String dmc;

    @ColumnInfo(name = "colour")
    private String colour;

    @ColumnInfo(name = "amountOwned")
	private double amountOwned;

	public ThreadDatabaseItem(String dmc,
			String colour,
			double amountOwned) {
		this.dmc = dmc;
		this.colour = colour;
		this.amountOwned = amountOwned;
	}

	public String getDmc() {
		return this.dmc;
	}

	public String getColour() {
		return this.colour;
	}

	public double getAmountOwned() {
		return this.amountOwned;
	}
}
