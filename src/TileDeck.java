import	java.util.*;


public class TileDeck
{
	
	private static final Random RANDOM = new Random();
	
	private	ArrayList<Tile>	deck = new ArrayList<>(144);

	public TileDeck()
	{
		for (int i = 0; i < 4; i++)
		{
			// CharacterTiles
			for (char c = '1'; c <= '9'; c++)
				deck.add(new CharacterTile(c));
			deck.add(new CharacterTile('N'));
			deck.add(new CharacterTile('E'));
			deck.add(new CharacterTile('W'));
			deck.add(new CharacterTile('S'));
			deck.add(new CharacterTile('C'));
			deck.add(new CharacterTile('F'));

			// CircleTiles
			for (int j = 1; j < 10; j++)
				deck.add(new CircleTile(j));

			// BambooTiles
			for (int j = 2; j < 10; j++)
				deck.add(new BambooTile(j));

			// Special Tiles
			deck.add(new WhiteDragonTile());
			deck.add(new Bamboo1Tile());
		}

		// FlowerTiles
		deck.add(new FlowerTile("Chrysanthemum"));
		deck.add(new FlowerTile("Orchid"));
		deck.add(new FlowerTile("Plum"));
		deck.add(new FlowerTile("Bamboo"));

		// SeasonTiles
		deck.add(new SeasonTile("Spring"));
		deck.add(new SeasonTile("Summer"));
		deck.add(new SeasonTile("Fall"));
		deck.add(new SeasonTile("Winter"));
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public void shuffle(long gameNumber) {
		RANDOM.setSeed(gameNumber);
		Collections.shuffle(deck,RANDOM);
	}
	
	public Tile deal() {
		if (deck.size() <= 0) return null;
		return deck.remove(deck.size() - 1);
	}
	
	
}

