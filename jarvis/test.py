import speech_recognition
import pyttsx

speech_engine = pyttsx.init('espeak') # see http://pyttsx.readthedocs.org/en/latest/engine.html#pyttsx.init
#speech_engine.setProperty('rate', 150)

def speak(text):
    speech_engine.say(text)
    speech_engine.runAndWait()

recognizer = speech_recognition.Recognizer()

def listen():
    microphone_index = 0
    for i, name in enumerate(speech_recognition.Microphone.list_microphone_names()):
        if name == "pulse":
            microphone_index = i
    #return "car"

    with speech_recognition.Microphone(device_index=microphone_index) as source:
        recognizer.adjust_for_ambient_noise(source)
        print("Listening...")
        audio = recognizer.listen(source)
        print(audio)

    try:
        pass
        text = recognizer.recognize_sphinx(audio, language = "en-US")
        return text
        #return recognizer.recognize_google(audio)
    except speech_recognition.UnknownValueError:
        print("Could not understand audio")
    except speech_recognition.RequestError as e:
        print("Recog Error; {0}".format(e))

    return ""


while True:
    print("text:",listen())
#speak("I heard you say " + listen())
#time.sleep(5)
