posts = [
    "I hate this platform http://abc.com",
    "Today is a good day",
    "This is a bad example",
    "Visit http://python.org for learning",
    "That was a toxic comment",
    "Nothing to report here",
    "hate bad toxic guy"
]

banned_words = ["bad", "toxic", "hate"]

links = []

report = {
    "User123": 3,
    "User456": 0,
    "User789": 1
}

cleaned = 0
blocked = 0

# Loop through every post
for post in posts:

    text = post

    # Check each banned word in the current post
    for word in banned_words:

        if word in text.lower():
            text = text.replace(word, "***")
            cleaned += 1

    words = text.split()

    # Check every word for website links
    for item in words:

        if item.startswith("http"):
            links.append(item)

    print(text)

# Save every extracted link into the text file
with open("links_found.txt", "w") as file:

    for item in links:
        file.write(item + "\n")

# Count posts that still contain banned words
for post in posts:

    flag = False

    # Check every banned word again
    for word in banned_words:

        if word in post.lower():
            flag = True

    if flag:
        blocked += 1

print("\nModerator Report")

# Display report of every user
for user in report:
    print(user, ":", report[user])

print("\nSummary")
print("Total Posts Screened :", len(posts))
print("Cleaned :", cleaned)
print("Blocked :", blocked)
print("Links Found :", len(links))