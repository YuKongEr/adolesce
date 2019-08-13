
def binary_search(items, target):
    """
    二分查找
    :param items:  目标数组
    :param target:  查找目标数
    :return:  返回下标 不存在 则返回-1
    """
    low = 0
    high = len(items) - 1
    while low <= high:
        mid = int((low + high) / 2)
        if items[mid] == target:
            return mid
        elif items[mid] < target:
            low = mid + 1
        else:
            high = mid - 1
    return -1


def main():
    items = [1, 2, 3, 4, 5]
    target = 5
    print(binary_search(items, target))


if __name__ == '__main__':
    main()
