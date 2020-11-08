
public class RankTile extends Tile {
	protected int rank;
	
	public RankTile(int rank) {
		this.rank = rank;
	}
	
	public boolean matches(Tile other) {
		return super.matches(other) && ((RankTile)other).rank == rank;
	}

}
