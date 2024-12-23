#!/bin/bash

create_wise_saying_json() {
  local id=$1
  local author=$2
  local content=$3

  echo "{
  \"id\": $id,
  \"author\": \"$author\",
  \"content\": \"$content\"
}" > "$id.json"

  echo "Saved: $id.json"
}

create_dummy() {
  create_wise_saying_json 1 "Albert Einstein" "Life is like riding a bicycle. To keep your balance, you must keep moving."
  create_wise_saying_json 2 "Oscar Wilde" "Be yourself; everyone else is already taken."
  create_wise_saying_json 3 "Nelson Mandela" "It always seems impossible until it’s done."
  create_wise_saying_json 4 "Mahatma Gandhi" "Be the change that you wish to see in the world."
  create_wise_saying_json 5 "Steve Jobs" "Your work is going to fill a large part of your life, and the only way to be truly satisfied is to do what you believe is great work."
  create_wise_saying_json 6 "Walt Disney" "The way to get started is to quit talking and begin doing."
  create_wise_saying_json 7 "Confucius" "Our greatest glory is not in never falling, but in rising every time we fall."
  create_wise_saying_json 8 "Eleanor Roosevelt" "The future belongs to those who believe in the beauty of their dreams."
  create_wise_saying_json 9 "Mark Twain" "The secret of getting ahead is getting started."
  create_wise_saying_json 10 "Aristotle" "Knowing yourself is the beginning of all wisdom."
  create_wise_saying_json 11 "Maya Angelou" "You may not control all the events that happen to you, but you can decide not to be reduced by them."
  create_wise_saying_json 12 "John Lennon" "Life is what happens when you’re busy making other plans."
  create_wise_saying_json 13 "Vincent Van Gogh" "I dream my painting and I paint my dream."
  create_wise_saying_json 14 "Albert Einstein" "Imagination is more important than knowledge."
  create_wise_saying_json 15 "Leonardo da Vinci" "Learning never exhausts the mind."
  create_wise_saying_json 16 "Yoda" "Do, or do not. There is no try."
}

create_dummy
