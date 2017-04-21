package menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import client.*;

public class MovieSearchMenu {
	private HashMap<String,Movie> movies;
	
	public MovieSearchMenu(HashMap<String, Movie> movies)
	{
		this.movies = movies;
	}
	
	
	@SuppressWarnings("resource")
	public void show()
	{
		
		Scanner scan = new Scanner(System.in);
		boolean search = true;
		while(search)
		{
			System.out.println("1. Search by Actor");
			System.out.println("2. Search by Title");
			System.out.println("3. Search by Genre");
			System.out.println("4. Back to Login Menu");
			String input = scan.next();
			switch(input)
			{
			case "1":
				search = searchByActor();
				break;
			case"2":
				search = searchByTitle();
				break;
			case"3":
				search = searchByGenre();
				break;
			case"4":
				return; 
			default:
				System.out.println("Invalid Input!");
			}
		}
	}
	
	@SuppressWarnings("resource")
	private boolean searchByActor()
	{
		System.out.print("Please enter the name of the actor you wish to search by.\n->");
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		ArrayList<String> result = searchByActorHelper(name);
		System.out.println(result.size()+" results:");
		for(int i  = 0 ;i < result.size();i++)
		{
			System.out.println("\""+result.get(i)+"\"");
		}
		return continueSearch();
	}
	
	//helper function to search movies by actor name
	//return a list of movies given a actor name
	public ArrayList<String> searchByActorHelper(String name)
	{
		String nameToLower = name.toLowerCase();
		ArrayList<String> result = new ArrayList<String>();
		Iterator<Movie> it = movies.values().iterator();
		while(it.hasNext())
		{
			Movie movie = it.next();
			for(int i = 0; i < movie.getActors().size();i++)
			{
				if(nameToLower.equals(movie.getActors().get(i).getFullname().toLowerCase()))
				{
					result.add(movie.getTitle());
					break;
				}
			}
		}
		return result;
	}
	
	@SuppressWarnings("resource")
	private boolean searchByTitle()
	{
		System.out.print("Please enter the name of the movie you wish to search by.\n->");
		Scanner scan = new Scanner(System.in);
		String title = scan.nextLine();
		ArrayList<String>result = searchByTitleHelper(title);
		System.out.println(result.size()+" results:");
		for(int i  = 0 ;i < result.size();i++)
		{
			System.out.println("\""+result.get(i)+"\"");
		}
		return continueSearch();
		
	}
	
	public ArrayList<String> searchByTitleHelper(String title)
	{
		ArrayList<String> result = new ArrayList<String>();
		String titleToLower = title.toLowerCase();
		Iterator<Movie> it = movies.values().iterator();
		while(it.hasNext())
		{
			Movie movie = it.next();
			if(titleToLower.equals(movie.getTitle().toLowerCase()))
			{
				result.add(movie.getTitle());
			}
		}
		return result;
	}
	
	private boolean searchByGenre()
	{
		System.out.print("Please enter the genre of the movie you wish to search by.\n->");
		Scanner scan = new Scanner(System.in);
		String genre = scan.nextLine();
		ArrayList<String>result = searchByGenreHelper(genre);
		System.out.println(result.size()+" results:");
		for(int i  = 0 ;i < result.size();i++)
		{
			System.out.println("\""+result.get(i)+"\"");
		}
		return continueSearch();
	}
	
	public ArrayList<String> searchByGenreHelper(String genre)
	{
		ArrayList<String> result = new ArrayList<String>();
		String genreToLower = genre.toLowerCase();
		Iterator<Movie> it = movies.values().iterator();
		while(it.hasNext())
		{
			Movie movie = it.next();
			if(genreToLower.equals(movie.getGenre().toLowerCase()))
			{
				result.add(movie.getTitle());
			}
		}
		return result;
	}
	
	//helper function
	//return true if the user wish to keep searching for movies
	//return false if the user wish to go back to login menu
	@SuppressWarnings("resource")
	private boolean continueSearch()
	{
		while(true)
		{
			System.out.println("\nPlease choose from the following options:");
			System.out.println("1. Back to Login Menu");
			System.out.println("2. Back to Search Movies Menu");
			Scanner scan = new Scanner(System.in);
			String input = scan.next();
			if(input.equals("1"))
			{
				return false;
			}
			else if(input.equals("2"))
			{
				return  true;
			}
			else
			{
				System.out.println("Invalid Input!");
			}
		}
			
	}
	
	
}
