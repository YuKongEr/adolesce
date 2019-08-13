def select_sort(items):
    for i in range(0, len(items) - 1):
        k = i
        for j in range(i + 1, len(items)):
            if items[j] < items[k]:
                k = j
        if k != i:
            items[i], items[k] = items[k], items[i]
    return items


def main():
    items = [1, 3, 1, 5, 6]
    print(select_sort(items))


if __name__ == '__main__':
    main()

