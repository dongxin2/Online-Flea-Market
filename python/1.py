# import stuff


from random import randint

import numpy as np
import torch

# Load model
from models import InferSent
model_version = 1
MODEL_PATH = "infersent2.pickle"
params_model = {'bsize': 64, 'word_emb_dim': 300, 'enc_lstm_dim': 2048,
                'pool_type': 'max', 'dpout_model': 0.0, 'version': model_version}
model = InferSent(params_model)
model.load_state_dict(torch.load(MODEL_PATH))

# Keep it on CPU or put it on GPU
use_cuda = False
model = model.cuda() if use_cuda else model


# If infersent1 -> use GloVe embeddings. If infersent2 -> use InferSent embeddings.
W2V_PATH = '../dataset/GloVe/glove.840B.300d.txt' if model_version == 1 else '../dataset/fastText/crawl-300d-2M.vec'
model.set_w2v_path(W2V_PATH)


# Load embeddings of K most frequent words
model.build_vocab_k_words(K=100000)

# Load some sentences
sentences = []
with open('samples.txt') as f:
    for line in f:
        sentences.append(line.strip())
print(len(sentences))

sentences[:5]

embeddings = model.encode(sentences, bsize=128, tokenize=False, verbose=True)
print('nb sentences encoded : {0}'.format(len(embeddings)))

np.linalg.norm(model.encode(['the cat eats.']))

def cosine(u, v):
    return np.dot(u, v) / (np.linalg.norm(u) * np.linalg.norm(v))

# print(cosine(model.encode(['multi action light day cream'])[0], model.encode(['a fresh fast absorbing serum'])[0]))
functions = []
with open('func.txt') as f:
    for line in f:
        functions.append(line.strip())

similar_func = np.zeros((len(functions), len(functions)))
for i in range(len(functions)):
    for j in range(len(functions)):
        similar_func[i][j] = cosine(model.encode([functions[i]])[0], model.encode([functions[j]])[0])

print(similar_func)

top = 3
most_similar = np.zeros((len(functions), 3),dtype=np.int32)
for i in range(len(functions)):
    row = similar_func[i]
    ind = np.argpartition(row, -4)[-4:]
    for k in range(3):
        most_similar[i][k] = ind[k]
    
print("------------------------")
print(most_similar)
print("------------------------")


descriptions = []
with open('desc.txt') as f:
    for line in f:
        descriptions.append(line.strip())

similar_desc = np.zeros((len(descriptions), len(descriptions)))
for i in range(len(descriptions)):
    for j in range(len(descriptions)):
        similar_desc[i][j] = cosine(model.encode([descriptions[i]])[0], model.encode([descriptions[j]])[0])
print('--------------------------------')
print(similar_desc)
print('--------------------------------')
functodec = np.zeros((len(descriptions),3))
for i in range(len(descriptions)):
    for j in range(3):
        functodec[i][j] = similar_desc[i][most_similar[i][j]]


