package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Preconditions;

import game.Game;

public class JSONDao extends JSONDataGameReaderDao implements IDao
{
	@Autowired
	private String savesDirectoryName;
	
	@Autowired
	private String savesFileName;
	
	private ObjectMapper mapperForGame;
	
	
	public JSONDao()
	{
		mapperForGame = new ObjectMapper();

		mapperForGame.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		mapperForGame.setVisibility(PropertyAccessor.CREATOR, Visibility.NONE);

		mapperForGame.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
	
	

	private Game[] getGames(Predicate<Game> predicate) throws IOException {
		File file = new File(getCompleteDaoPathFile(savesFileName));
		Game[] array = mapperForGame.readValue(file,Game[].class);
		Stream<Game> stream=Stream.of(array);
		stream=stream.filter(predicate);
		List<Game> list=stream.collect(Collectors.toList());
		return list.toArray(new Game[0]);
	}

	@Override
	public Game[] getGames() throws IOException {
		return getGames(g->true);
	}

	@Override
	public boolean gameExists(Game game) throws IOException {
		return getGames(g -> g.getName().compareTo(game.getName())==0).length > 0;
	}

	@Override
	public void newGame(Game game) throws IOException {
		Preconditions.checkArgument(!gameExists(game), "A game with this same name already exists");
		
		add(savesFileName, game, Game[].class);
	}

	@Override
	public Game loadGame(String name) throws IOException
	{
		return getGames(g->name.compareTo(g.getName())==0)[0];
	}

	@Override
	public void saveGame(Game game) throws IOException {
		deleteGame(game);
		newGame(game);
	}

	@Override
	public void deleteGame(Game game) throws IOException {
		File file = new File(getCompleteDaoPathFile(savesFileName));
		Game[] array = mapperForGame.readValue(file,Game[].class);
		Stream<Game> stream=Stream.of(array);
		stream=stream.filter(g->g.getName().compareTo(game.getName())!=0);
		List<Game> list=stream.collect(Collectors.toList());
		mapper.writeValue(file, list);
	}
	
	
	
	
	
	private <T> void add(String fileName, T t, Class<T[]> tClass) throws IOException
	{
		File file = new File(getCompleteDaoPathFile(fileName));
		T[] tArray = mapperForGame.readValue(file,tClass);
		ArrayList<T> tArrayList=new ArrayList<>(Arrays.asList(tArray));
		tArrayList.add(t);
		mapper.writeValue(file, tArrayList);
	}
	

	
	private String getCompleteDaoPathFile(String fileName) throws IOException
	{
		return getProjectPath()+savesDirectoryName+fileName+extentionName;
	}
}
