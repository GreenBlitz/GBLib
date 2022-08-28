#only works if DualSidedAngTarget has no package ¯\_(ツ)_/¯


RADIUS = 75
SIZE = 500
CENTER = (SIZE/2, SIZE/2)
KP = 0.1

JAVA_PATH = ""  # keep empty if java is set in path
import pygame
import math
import time
import subprocess
import json
import queue

pygame.init()

screen = pygame.display.set_mode((SIZE, SIZE))


def line_by_ang(ang, color):
    pygame.draw.line(screen, color, CENTER,
                     (SIZE / 2 + math.cos(2*math.pi*ang) * RADIUS, SIZE / 2 - math.sin(2*math.pi*ang) * RADIUS), 3)

cmd = f"{JAVA_PATH}javac DualSidedAngTarget.java"
process = subprocess.Popen(cmd)
process.wait()

head = 0
target = 10
tail = 0

error_q = queue.Queue()
for i in range(5):
    error_q.put(0)
running = True
while running:

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    screen.fill((255, 255, 255))

    pygame.draw.circle(screen, (0, 0, 0), CENTER, RADIUS, 3)

    line_by_ang(head, (0, 0, 255))
    line_by_ang(tail, (255, 0, 0))
    line_by_ang(target, (0, 255, 0))


    cmd = f"{JAVA_PATH}java DualSidedAngTarget {target} {head}"
    process = subprocess.Popen(cmd, stdout=subprocess.PIPE)
    process.wait()
    raw_data = process.stdout.readline().decode("utf-8")
    data = json.loads(raw_data)
    print(data)
    error = (data["target"] - data["start"])
    error_q.put(error)
    head += error_q.get() * KP
    head %= 1
    tail = head + 0.5
    tail %= 1



    pygame.display.flip()
pygame.quit()