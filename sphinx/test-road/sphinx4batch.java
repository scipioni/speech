
/*
   Copyright 2010-2013 by Bits and Pixels, Inc.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

/**
 * sphinx4batch is a program to test just the recognition capability of
 * sphinx4 on a set of recorded questions. This is for comparison with
 * the same test using pocketsphinx_batch (included with pocketsphinx.)
 */

import java.io.*;
import java.net.URL;
import java.util.TreeMap;
import javax.sound.sampled.*;

import edu.cmu.sphinx.frontend.util.AudioFileDataSource;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;


public class sphinx4batch extends Thread {

	static String audiodir;
	static String config = "batch.xml";

	public sphinx4batch () {
		processSpeech ();
	}

	void processSpeech () {
		try {
			BufferedReader in = new BufferedReader (
					new FileReader ("road.questions"));
			TreeMap <String, String> original =
					new TreeMap <String, String> ();
			String line;
			while ((line = in.readLine ()) != null) {
				int pos = line.indexOf ("\t");
				if (pos == -1) continue;
				String tag = line.substring (1, pos);
				String val = line.substring (pos+1);
				original.put (tag, val);
			}
			in.close ();
			ConfigurationManager cm = new ConfigurationManager (
					sphinx4batch.class.getResource (config));

			Recognizer recognizer = (Recognizer) cm.lookup ("recognizer");
			recognizer.allocate ();
			for (int i=1; i<10; i++) {
				StringBuffer sb = new StringBuffer ();
				sb.append (audiodir);
				sb.append ("road");
				sb.append ("0"+i);
				sb.append (".wav");
				String filename = new String (sb);
				System.out.println (filename);
				URL audioURL = new File (filename).toURI ().toURL ();
				AudioFileDataSource dataSource = (AudioFileDataSource) cm.lookup ("audioFileDataSource");
				dataSource.setAudioFile (audioURL, null);

				Result result;

				String recognized = null;
				String response = null;
				while ((result = recognizer.recognize ()) != null) {
					String resultText = result.getBestResultNoFiller ();
					String orig = original.get ("0"+i);
					System.out.println ("Original  : "+orig);
					System.out.println ("Recognized: " + resultText);
					recognized = resultText;
					break;
				}
			}
			in.close ();
		} catch (Exception e) {
			e.printStackTrace ();
		}
	}

	public static void main (String args []) {
		if (args.length > 0) {
			audiodir = args [0];
			if (!audiodir.endsWith ("/")) {
				audiodir = audiodir + "/";
			}
			new sphinx4batch ();
		}
		else {
			System.out.println ("java sphinx4batch audiodirectory");
		}
	}

}
