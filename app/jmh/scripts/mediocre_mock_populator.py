import json
import random
import os

output_dir = "mock_books_files"
os.makedirs(output_dir, exist_ok=True)

book_variations = [
    {"title": "1984", "author": "George Orwell", "year_published": 1949, "genre": "Dystopian, Political Fiction"},
    {"title": "Pride and Prejudice", "author": "Jane Austen", "year_published": 1813, "genre": "Romance, Satire"},
    {"title": "Romeo and Juliet", "author": "William Shakespeare", "year_published": 1597, "genre": "Romance, Tragedy"},
    {"title": "Moby Dick", "author": "Herman Melville", "year_published": 1851, "genre": "Adventure, Epic"},
    {"title": "Hamlet", "author": "William Shakespeare", "year_published": 1603, "genre": "Tragedy, Drama"},
    {"title": "The Great Gatsby", "author": "F. Scott Fitzgerald", "year_published": 1925, "genre": "Tragedy, Social Commentary"},
    {"title": "War and Peace", "author": "Leo Tolstoy", "year_published": 1869, "genre": "Historical, Philosophical"},
    {"title": "The Catcher in the Rye", "author": "J.D. Salinger", "year_published": 1951, "genre": "Coming-of-age, Realism"},
    {"title": "To Kill a Mockingbird", "author": "Harper Lee", "year_published": 1960, "genre": "Social Commentary, Legal Story"},
    {"title": "The Odyssey", "author": "Homer", "year_published": -700, "genre": "Epic, Adventure"},
    {"title": "Crime and Punishment", "author": "Fyodor Dostoevsky", "year_published": 1866, "genre": "Psychological, Crime"},
    {"title": "Jane Eyre", "author": "Charlotte Bronte", "year_published": 1847, "genre": "Romance, Gothic"},
    {"title": "Brave New World", "author": "Aldous Huxley", "year_published": 1932, "genre": "Dystopian, Science Fiction"},
    {"title": "The Iliad", "author": "Homer", "year_published": -750, "genre": "Epic, War"},
    {"title": "Anna Karenina", "author": "Leo Tolstoy", "year_published": 1877, "genre": "Romance, Tragedy"},
    {"title": "Frankenstein", "author": "Mary Shelley", "year_published": 1818, "genre": "Gothic, Science Fiction"},
    {"title": "Dracula", "author": "Bram Stoker", "year_published": 1897, "genre": "Horror, Gothic"},
    {"title": "The Hobbit", "author": "J.R.R. Tolkien", "year_published": 1937, "genre": "Fantasy, Adventure"},
    {"title": "Les Misérables", "author": "Victor Hugo", "year_published": 1862, "genre": "Historical, Drama"},
    {"title": "Great Expectations", "author": "Charles Dickens", "year_published": 1861, "genre": "Social Commentary, Bildungsroman"},
    {"title": "Macbeth", "author": "William Shakespeare", "year_published": 1606, "genre": "Tragedy, Drama"},
    {"title": "A Tale of Two Cities", "author": "Charles Dickens", "year_published": 1859, "genre": "Historical, Drama"},
    {"title": "Wuthering Heights", "author": "Emily Bronte", "year_published": 1847, "genre": "Gothic, Romance"},
    {"title": "The Brothers Karamazov", "author": "Fyodor Dostoevsky", "year_published": 1880, "genre": "Philosophical, Psychological"},
    {"title": "The Divine Comedy", "author": "Dante Alighieri", "year_published": 1320, "genre": "Epic, Allegory"},
    {"title": "The Count of Monte Cristo", "author": "Alexandre Dumas", "year_published": 1844, "genre": "Adventure, Historical"},
    {"title": "The Picture of Dorian Gray", "author": "Oscar Wilde", "year_published": 1890, "genre": "Philosophical, Gothic"},
    {"title": "Catch-22", "author": "Joseph Heller", "year_published": 1961, "genre": "Satire, War"},
    {"title": "Don Quixote", "author": "Miguel de Cervantes", "year_published": 1605, "genre": "Adventure, Satire"},
    {"title": "One Hundred Years of Solitude", "author": "Gabriel Garcia Marquez", "year_published": 1967, "genre": "Magical Realism, Family Saga"},
    {"title": "Fahrenheit 451", "author": "Ray Bradbury", "year_published": 1953, "genre": "Dystopian, Science Fiction"},
    {"title": "The Stranger", "author": "Albert Camus", "year_published": 1942, "genre": "Philosophical, Absurdist"},
    {"title": "Meditations", "author": "Marcus Aurelius", "year_published": 180, "genre": "Philosophy, Stoicism"},
    {"title": "The Metamorphosis", "author": "Franz Kafka", "year_published": 1915, "genre": "Absurdist, Psychological"},
    {"title": "The Trial", "author": "Franz Kafka", "year_published": 1925, "genre": "Psychological, Legal Thriller"},
    {"title": "Lolita", "author": "Vladimir Nabokov", "year_published": 1955, "genre": "Psychological, Controversial"},
]

num_files = 200
max_rows_per_file = 10_000

for file_idx in range(1, num_files + 1):
    # Random number of rows for this file (e.g., 1,000 to 10,000)
    num_rows = random.randint(1000, max_rows_per_file)

    file_path = os.path.join(output_dir, f"mediocre_bookshelve_{file_idx}.json")
    with open(file_path, "w") as f:
        f.write("[\n")
        for i in range(num_rows):
            book = random.choice(book_variations).copy()
            # Make slight variations for uniqueness
            book["title"] += f" #{random.randint(1, 1000)}"
            book["year_published"] += random.randint(0, 5)

            f.write(json.dumps(book))
            if i != num_rows - 1:
                f.write(",\n")
        f.write("\n]")

print(f"Generated {num_files} files in '{output_dir}'")
