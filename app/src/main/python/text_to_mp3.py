from gtts import gTTS

def text_to_mp3(text, file_path):
    tts = gTTS(text, lang="en")
    tts.save(file_path)