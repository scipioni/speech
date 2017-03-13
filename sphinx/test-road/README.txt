
Comparing Sphinx4 with Pocketsphinx

We have had good recognition results with sphinx4 (for an American
English speaker using a noise cancelling microphone.) We wanted to see
if sphinx4 compares favorably with Pocketsphinx.

Pocketsphinx is the Sphinx version for mobile platforms. Our test uses
the same audio files and models for testing Sphinx4 and Pocketsphinx.

The audio files in this case are recordings we have made for other
applications in the Jaivox download. To run the tests here, you need to
download the files in pocket.zip at
http://www.jaivox.com/sites/default/files/downloads/pocket.zip. This
includes the audio files needed for this test (though more such files
are included in the main Jaivox download.)

To perform this test, you need to install both sphinx4 and pocketsphinx
following the instructions at their respective sites

sphinx4 from http://cmusphinx.sourceforge.net/sphinx4/
pocketsphinx from http://sourceforge.net/projects/cmusphinx/files/pocketsphinx/

( Our tests were done with Pocketsphinx version 0.7 and Sphinx4 
version 1.0beta6)

Both these downloads have other requirements such as sphinxbase for
pocketsphinx and ant for sphinx4. Please see the above links for
details.

After you have installed Pocketsphinx, you can test whether it works
using the following command from the src/programs directory of the
Pocketsphinx installation:

./pocketsphinx_batch \
-hmm ../../model/hmm/en_US/hub4wsj_sc_8k \
-adcin yes \
-lm ../../model/lm/en_US/hub4.5000.DMP \
-dict ../../model/lm/en_US/cmu07a.dic \
-ctl ../../test/data/wsj/test5k.n800.ctl \
-cepdir ../../test/data/wsj \
-cepext .wav \
-backtrace yes

(This tests ../../test/data/wsj/test5k.n800.ctl which contains names of wav 
files.)

In the output look for lines of the form 

INFO: pocketsphinx.c(845): n800_440c0207: part of on the that that a couple things (-34019)

This shows the recognition results (there are other lines like this in
the output.) Since the test files are .wav files, you can listen to them
to see what they really are saying.

These recognition results are not perfect, but we want to compare how
the same kind of test works on some of our audio files, using the WSJ
audio model we have used in our sphinx4 tests.

To do so, we used an audio model we have built using the online language
modelling tool (please see the article
http://www.jaivox.com/onlinelm.html.)

For the rest of this article, we will assume some locations for various
files needed for the test.

/home/me/jaivox/tests/pocket contains all the files you downloaded in pocket.zip.

/home/me/classes/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz contains the audio model files from sphinx4

For convenience you can define some variables to point to these locations, we will use

export pdir=/home/me/jaivox/tests/pocket
export wsjhmm=/home/me/classes/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz

./pocketsphinx_batch \
-adcin yes \
-hmm $wsjhmm \
-lm $pdir/road.arpabo.DMP \
-dict $pdir/road.dic \
-ctl $pdir/road.ctl \
-cepdir $pdir \
-cepext .wav \
-backtrace yes

Collecting the relevant output lines from this, we get the following recognition resutls for the audio files used in this test. The audio files road01.wav to road09.wav contain the following questions, recorded using a noise-cancelling microphone

001	do the roads get slow at this time
002	do the roads get congested at this time
003	are roads busy
004	do you think the roads are slow
005	do you think elmwood avenue is slow
006	do you think old mill road is fast
007	do you think old mill road is quick
008	is elmwood avenue a fast highway
009	is avenue o the quickest route

But here are the results we obtained.

INFO: pocketsphinx.c(845): road01: OF AND MOST HOW SMOOTH THAN MORE (-27358)
INFO: pocketsphinx.c(845): road02: GO THAN MOST TIME UHUH HOW ROUTE O THE ROUTE GET (-36970)
INFO: pocketsphinx.c(845): road03: SMOOTH THE HOW (-15433)
INFO: pocketsphinx.c(845): road04: HIGHWAY TIME MILL MILL ROAD OLD ROUTE (-22602)
INFO: pocketsphinx.c(845): road05: HOW FAST SMOOTH ROAD ONE OF ROUTE (-29388)
INFO: pocketsphinx.c(845): road06: HIGHWAY TIME A MOST THAN O MOST ARE ROAD (-29411)
INFO: pocketsphinx.c(845): road07: HIGHWAY STOP AND GO THAN ROUTE MILL A AND GO A (-31305)
INFO: pocketsphinx.c(845): road08: ROUTE MILL O ONE AND ROAD ONE OF ARE ROUTE UHUH A (-33973)
INFO: pocketsphinx.c(845): road09: HOW THAN SMOOTH ARE THE MORE (-25640)

By comparison, the program sphinx4batch in the downloaded files produces
much better results on the same audio files using the same audio model
and other files. Note the argument "." at the end of the command to
indicate that audio files are in the same directory as the downloaded
files (/home/me/jaivox/tests/pocket as assumed.)

java sphinx4batch .

(ignoring some diagnostic output from sphinx)

./road01.wav
Original  : do the roads get slow at this time
Recognized: do the roads get slow at this time
./road02.wav
Original  : do the roads get congested at this time
Recognized: do the routes get congested at this time
./road03.wav
Original  : are roads busy
Recognized: are roads busy
./road04.wav
Original  : do you think the roads are slow
Recognized: you think the roads are slow
./road05.wav
Original  : do you think elmwood avenue is slow
Recognized: you think elmwood avenue a slow
./road06.wav
Original  : do you think old mill road is fast
Recognized: do you think old old road is fast
./road07.wav
Original  : do you think old mill road is quick
Recognized: you think old mill road is quick
./road08.wav
Original  : is elmwood avenue a fast highway
Recognized: is elmwood avenue a fast highway
./road09.wav
Original  : is avenue o the quickest route
Recognized: is i than you o the quickest route

The recognition here is nearly perfect; there are a few dropped "do"
words in the questions and the last one, road9.wav is not recognized
very well.




