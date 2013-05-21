package com.pkp.gameengine.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import com.pkp.gameengine.i.io.IFileIO;

public class Settings {
	public static boolean soundEnabled = true;
	public static Map<Integer, Integer> scores = new HashMap<Integer, Integer>();

	public static void load(IFileIO files) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
					files.readFile(".mrnom")));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			for (int i = 0; i < 16; i++) {
				scores.put(i,Integer.parseInt(in.readLine()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) { // :/ It's
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {

			}
		}
	}

	public static void save(IFileIO files) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					files.writeFile(".mrnom")));
			out.write(Boolean.toString(soundEnabled));
			for (int i = 0; i < 16; i++) {
				if (scores.containsKey(i)) out.write(Integer.toString(scores.get(i)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
				{
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addScore(int level, int score) {
		scores.put(level, score);
	}
}