import numpy as np

def test():
  return np.absolute(-10)

def getX(pose):
  return np.array(pose)[:, 0]

def getY(pose):
  return np.array(pose)[:, 1]

def getZ(pose):
  return np.array(pose)[:, 2]

arr_frame = []

rd = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 79, 80, 82, 84, 85, 88, 90, 91, 94, 96, 97, 100, 102, 103, 105, 106, 108, 109, 112, 114, 115, 117, 118, 120, 121, 124, 127, 129, 130, 132, 133, 136, 147, 150, 159, 163, 166, 185, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 219, 220, 221, 222, 223, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 237, 238, 241, 244, 246, 247, 248, 249, 250, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 264, 265, 267, 268, 270, 271, 273, 274, 276, 277, 279, 280, 282, 283, 285, 286, 288, 291, 292, 294, 295, 297, 298, 300, 301, 303, 304, 306, 307, 308, 309, 310, 312, 313, 314, 315, 316, 318, 319, 322, 325, 328, 331, 333, 334, 337, 340, 343, 344, 345, 346, 347, 355, 358, 361, 364, 369, 372, 378, 381, 382, 384, 385, 387, 390, 391, 393, 394, 396, 397, 400, 403, 406, 408, 409, 411, 412, 417, 418, 420, 421, 429, 432, 438, 441, 442, 444, 445, 450, 453, 454, 456, 457, 462, 463, 465, 466, 468, 469, 470, 472, 474, 475, 477, 478, 481, 484, 486, 489, 493, 499, 500, 502, 504, 506, 511, 517, 518, 520, 522, 524, 525, 527, 528, 529, 530, 531, 532, 533, 534, 535, 536, 537, 538, 539, 543, 546, 552, 555, 556, 558, 559, 562, 565, 567, 568, 570, 571, 574, 577, 579, 580, 582, 583, 585, 586, 591, 592, 594, 595, 603, 606, 612, 615, 616, 618, 619, 624, 625, 627, 628, 630, 631, 634, 636, 637, 639, 640, 642, 643, 646, 648, 649, 651, 652, 654, 655, 658, 660, 662, 669, 675, 679, 681, 682, 684, 685, 686, 687, 688, 689, 690, 691, 692, 694, 696, 698, 699, 700, 701, 702, 703, 704, 705, 706, 707, 708, 709, 710, 711, 712, 713, 717, 720, 729, 730, 732, 733, 735, 738, 739, 741, 742, 744, 745, 748, 751, 753, 754, 756, 757, 759, 760, 766, 768, 769, 777, 780, 786, 787, 789, 790, 792, 793, 796, 798, 799, 801, 802, 804, 805, 808, 810, 811, 813, 814, 816, 817, 820, 826, 829, 840, 841, 843, 844, 845, 846, 847, 848, 850, 853, 854, 855, 856, 858, 859, 861, 862, 863, 865, 868, 870, 872, 875, 876, 877, 878, 880, 881, 882, 883, 884, 885, 886, 887, 894, 895, 898, 900, 903, 904, 906, 907, 909, 910, 912, 913, 915, 916, 918, 919, 922, 925, 927, 928, 930, 931, 933, 934, 939, 940, 942, 943, 949, 951, 952, 954, 955, 958, 960, 961, 963, 964, 966, 967, 970, 972, 973, 975, 976, 978, 979, 984, 987, 988, 990, 991, 994, 1002, 1003, 1004, 1009, 1010, 1012, 1013, 1014, 1015, 1016, 1017, 1018, 1019, 1020, 1021, 1022, 1023, 1024, 1025, 1026, 1027, 1028, 1030, 1031, 1032, 1033, 1034, 1035, 1036, 1037, 1038, 1039, 1040, 1041, 1042, 1043, 1044, 1046, 1049, 1050, 1051, 1052, 1054, 1055, 1056, 1057, 1058, 1059, 1060, 1061, 1062, 1065, 1068, 1072, 1074, 1077, 1078, 1080, 1081, 1083, 1089, 1090, 1092, 1093, 1095, 1096, 1101, 1102, 1104, 1105, 1110, 1111, 1113, 1114, 1116, 1117, 1119, 1120, 1123, 1125, 1126, 1129, 1132, 1137, 1138, 1140, 1141, 1146, 1149, 1150, 1153, 1156, 1158, 1164, 1165, 1167, 1168, 1170, 1173, 1174, 1176, 1177, 1179, 1183, 1184, 1186, 1187, 1188, 1189, 1190, 1191, 1192, 1193, 1194, 1196, 1197, 1198, 1199, 1200, 1201, 1202, 1204, 1205, 1206, 1207, 1208, 1209, 1210, 1212, 1215, 1218, 1220, 1221, 1223, 1224, 1225, 1226, 1227, 1228, 1229, 1230, 1231, 1232, 1233, 1234, 1235, 1239, 1242, 1245, 1246, 1248, 1251, 1254, 1255, 1257, 1263, 1264, 1266, 1267, 1273, 1275, 1276, 1278, 1279, 1281, 1282, 1285, 1287, 1288, 1291, 1294, 1299, 1302, 1303, 1308, 1315, 1320, 1326, 1327, 1330, 1332, 1333, 1335, 1336, 1338, 1339, 1341, 1342, 1344, 1347, 1348, 1350, 1351, 1357, 1358, 1360, 1361, 1362, 1363, 1364, 1365, 1366, 1367, 1368, 1369, 1370, 1371, 1372, 1373, 1374, 1375, 1376, 1378, 1379, 1380, 1381, 1382, 1383, 1384, 1386, 1387, 1388, 1389, 1390, 1391, 1392, 1393, 1394, 1395, 1397, 1398, 1399, 1400, 1402, 1403, 1404, 1405, 1406, 1407, 1408, 1409, 1412, 1413, 1416, 1419, 1420, 1422, 1425, 1426, 1428, 1429, 1431, 1435, 1437, 1438, 1440, 1441, 1443, 1444, 1446, 1447, 1449, 1450, 1452, 1453, 1456, 1458, 1459, 1461, 1462, 1464, 1465, 1468, 1488, 1489, 1491, 1494, 1495, 1497, 1498, 1500, 1501, 1503, 1504, 1506, 1507, 1509, 1510, 1512, 1513, 1515, 1516, 1518, 1521, 1522, 1524, 1525, 1527, 1531, 1532, 1534, 1536, 1537, 1539, 1540, 1542, 1543, 1544, 1545, 1546, 1547, 1548, 1549, 1550, 1552, 1553, 1554, 1555, 1556, 1557, 1558, 1559, 1560, 1561, 1562, 1563, 1564, 1565, 1566, 1568, 1569, 1571, 1572, 1575, 1576, 1577, 1578, 1579, 1580, 1581, 1582, 1583, 1587, 1590, 1596, 1597, 1599, 1600, 1602, 1603, 1605, 1606, 1608, 1609, 1611, 1612, 1614, 1615, 1618, 1621, 1623, 1624, 1626, 1627, 1630, 1635, 1636, 1639, 1650, 1656, 1657, 1659, 1660, 1662, 1663, 1665, 1668, 1669, 1671, 1672, 1674, 1675, 1677, 1678, 1680, 1681, 1683, 1684, 1686, 1687, 1689, 1690, 1692, 1695, 1696, 1698, 1699, 1705, 1706, 1708, 1709, 1710, 1711, 1713, 1714, 1716, 1717, 1718, 1719, 1720, 1721, 1722, 1723, 1724, 1727, 1728, 1729, 1730, 1731, 1732, 1734, 1737, 1748, 1755, 1759, 1761, 1762, 1764, 1765, 1767, 1768, 1770, 1771, 1773, 1774, 1776, 1777, 1779, 1780, 1782, 1783, 1785, 1786, 1788, 1789, 1794, 1797, 1798, 1800, 1801, 1804, 1812, 1813, 1815, 1816, 1824, 1830, 1831, 1834, 1836, 1837, 1842, 1843, 1845, 1846, 1848, 1849, 1852, 1855, 1857, 1858, 1860, 1861, 1864, 1866, 1869, 1870, 1872, 1873, 1879, 1880, 1882, 1884, 1885, 1887, 1888, 1890, 1893, 1896, 1903, 1905, 1923, 1932, 1935, 1936, 1939, 1942, 1944, 1947, 1948, 1950, 1951, 1953, 1956, 1959, 1962, 1963, 1974, 1975, 1977, 1978, 1983, 1984, 1986, 1987, 1998, 2004, 2005, 2008, 2010, 2011, 2020, 2022, 2023, 2026, 2028, 2029, 2031, 2032, 2034, 2035, 2038, 2040, 2043, 2044, 2046, 2047, 2052, 2053, 2055, 2056, 2059, 2061, 2062, 2063, 2065, 2066, 2067, 2068, 2070, 2077, 2083, 2085, 2086, 2089, 2096, 2109, 2112, 2113, 2118, 2121, 2124, 2125, 2133, 2136, 2137, 2139, 2140, 2142, 2143, 2145, 2146, 2148, 2149, 2151, 2152, 2157, 2158, 2160, 2161, 2172, 2182, 2184, 2185, 2190, 2191, 2193, 2194, 2196, 2197, 2200, 2202, 2203, 2205, 2206, 2208, 2209, 2212, 2214, 2217, 2218, 2220, 2221, 2226, 2227, 2230, 2233, 2236, 2245, 2246, 2248, 2249, 2251, 2254, 2257, 2260, 2261, 2298, 2299, 2301, 2304, 2305, 2307, 2308, 2310, 2311, 2313, 2314, 2316, 2317, 2319, 2320, 2322, 2323, 2325, 2326, 2328, 2331, 2332, 2334, 2335, 2346, 2352, 2356, 2358, 2359, 2364, 2365, 2367, 2368, 2370, 2371, 2374, 2376, 2377, 2379, 2380, 2382, 2383, 2386, 2388, 2391, 2392, 2394, 2395, 2407, 2410, 2412, 2413, 2415, 2416, 2418, 2419, 2420, 2421, 2422, 2425, 2428, 2431, 2453, 2460, 2463, 2466, 2469, 2472, 2473, 2475, 2478, 2479, 2481, 2482, 2484, 2485, 2487, 2488, 2490, 2491, 2493, 2494, 2496, 2497, 2499, 2500, 2502, 2505, 2506, 2508, 2509, 2517, 2520, 2526, 2527, 2529, 2530, 2532, 2533, 2538, 2539, 2541, 2542, 2544, 2545, 2548, 2550, 2551, 2553, 2554, 2556, 2557, 2562, 2565, 2568, 2569, 2570, 2574, 2575, 2576, 2577, 2578, 2580, 2581, 2583, 2584, 2587, 2590, 2595, 2599, 2607, 2631, 2634, 2640, 2643, 2646, 2647, 2649, 2653, 2655, 2656, 2658, 2659, 2662, 2667, 2668, 2670, 2671, 2679, 2680, 2682, 2683, 2694, 2700, 2703, 2704, 2706, 2707, 2712, 2713, 2715, 2716, 2718, 2719, 2724, 2730, 2731, 2734, 2736, 2737, 2739, 2740, 2742, 2743, 2748, 2749, 2752, 2755, 2758, 2773, 2776, 2777, 2784, 2790, 2791, 2796, 2797, 2805, 2808, 2814, 2817, 2820, 2821, 2823, 2829, 2832, 2833, 2839, 2841, 2842, 2844, 2845, 2847, 2853, 2854, 2856, 2857, 2868, 2874, 2878, 2880, 2881, 2892, 2893, 2896, 2898, 2899, 2901, 2902, 2904, 2905, 2908, 2911, 2913, 2914, 2916, 2917, 2922, 2929, 2947, 2950, 2951, 2958, 2964, 2970, 2979, 2982, 2991, 2994, 2995, 3000, 3003, 3004, 3006, 3007, 3013, 3015, 3016, 3018, 3019, 3021, 3027, 3028, 3030, 3031, 3042, 3054, 3055, 3060, 3061, 3063, 3064, 3066, 3067, 3070, 3072, 3073, 3075, 3076, 3078, 3079, 3084, 3090, 3091, 3108, 3111, 3120, 3121, 3124, 3153, 3156, 3162, 3165, 3168, 3169, 3171, 3174, 3175, 3177, 3178, 3180, 3181, 3183, 3187, 3189, 3190, 3192, 3193, 3195, 3201, 3202, 3204, 3205, 3216, 3217, 3222, 3223, 3225, 3226, 3228, 3229, 3234, 3235, 3237, 3238, 3240, 3241, 3252, 3253, 3262, 3264, 3265, 3276, 3277, 3280, 3282, 3285, 3288, 3290, 3294, 3295, 3297, 3298, 3300, 3303, 3327, 3330, 3336, 3339, 3342, 3343, 3345, 3348, 3349, 3351, 3352, 3354, 3355, 3357, 3360, 3361, 3363, 3364, 3366, 3367, 3378, 3379, 3390, 3391, 3396, 3397, 3400, 3402, 3403, 3415, 3420, 3424, 3427, 3432, 3435, 3436, 3438, 3439, 3450, 3451, 3456, 3459, 3462, 3464, 3469, 3474, 3477, 3501, 3504, 3510, 3513, 3516, 3519, 3522, 3523, 3525, 3526, 3528, 3529, 3540, 3541, 3549, 3550, 3552, 3553, 3564, 3565, 3577, 3589, 3594, 3595, 3597, 3598, 3600, 3601, 3604, 3606, 3609, 3610, 3612, 3613, 3624, 3625, 3633, 3636, 3643, 3645, 3646, 3651, 3675, 3678, 3684, 3687, 3690, 3696, 3697, 3699, 3702, 3703, 3714, 3715, 3723, 3724, 3726, 3727, 3738, 3747, 3748, 3750, 3751, 3756, 3759, 3763, 3774, 3775, 3778, 3783, 3784, 3786, 3787, 3801, 3810, 3817, 3849, 3852, 3858, 3861, 3864, 3873, 3876, 3877, 3885, 3888, 3889, 3897, 3898, 3900, 3901, 3918, 3919, 3922, 3924, 3925, 3937, 3946, 3949, 3957, 3958, 3960, 3961, 3972, 3973, 3981, 3984, 4023, 4026, 4032, 4035, 4038, 4047, 4050, 4057, 4059, 4062, 4063, 4074, 4075, 4086, 4092, 4093, 4096, 4098, 4099, 4108, 4110, 4111, 4116, 4117, 4119, 4123, 4134, 4135, 4149, 4165, 4197, 4200, 4206, 4209, 4212, 4219, 4221, 4224, 4231, 4233, 4236, 4237, 4245, 4246, 4248, 4249, 4260, 4267, 4273, 4278, 4279, 4281, 4282, 4284, 4285, 4297, 4306, 4308, 4309, 4320, 4321, 4329, 4332, 4339, 4342, 4374, 4383, 4386, 4395, 4398, 4405, 4407, 4410, 4419, 4422, 4423, 4434, 4440, 4441, 4446, 4447, 4452, 4453, 4456, 4459, 4462, 4467, 4468, 4470, 4471, 4480, 4483, 4497, 4498, 4500, 4503, 4513, 4516, 4548, 4557, 4560, 4569, 4572, 4579, 4581, 4584, 4593, 4596, 4597, 4608, 4615, 4620, 4621, 4626, 4627, 4630, 4633, 4636, 4638, 4639, 4641, 4642, 4644, 4645, 4656, 4657, 4662, 4677, 4687, 4690, 4722, 4734, 4743, 4746, 4753, 4755, 4758, 4767, 4770, 4771, 4795, 4800, 4801, 4804, 4807, 4812, 4813, 4816, 4818, 4819, 4827, 4828, 4830, 4831, 4843, 4861, 4864, 4896, 4905, 4908, 4917, 4920, 4927, 4929, 4932, 4941, 4944, 4945, 4956, 4974, 4975, 4978, 4981, 4986, 4987, 4990, 4993, 5001, 5002, 5004, 5005, 5010, 5012, 5013, 5017, 5035, 5038, 5070, 5079, 5082, 5094, 5106, 5115, 5118, 5119, 5130, 5136, 5155, 5160, 5161, 5164, 5166, 5167, 5175, 5176, 5178, 5179, 5191, 5209, 5244, 5253, 5256, 5268, 5280, 5289, 5292, 5293, 5304, 5310, 5334, 5335, 5338, 5340, 5341, 5349, 5350, 5353, 5365, 5368, 5383, 5386, 5430, 5442, 5454, 5463, 5466, 5478, 5484, 5496, 5497, 5514, 5515, 5523, 5524, 5526, 5527, 5532, 5539, 5542, 5553, 5557, 5584, 5604, 5616, 5628, 5637, 5640, 5646, 5647, 5648, 5649, 5650, 5652, 5653, 5655, 5656, 5658, 5659, 5661, 5662, 5664, 5665, 5667, 5668, 5670, 5671, 5673, 5674, 5676, 5677, 5679, 5680, 5682, 5683, 5685, 5686, 5688, 5689, 5691, 5692, 5697, 5698, 5700, 5701, 5703, 5704, 5706, 5708, 5709, 5712, 5713, 5716, 5731, 5778, 5790, 5802, 5814, 5826, 5832, 5844, 5845, 5856, 5857, 5860, 5863, 5874, 5875, 5880, 5886, 5887, 5889, 5890, 5895, 5904, 5905, 5907, 5908, 5952, 5964, 5988, 6000, 6006, 6018, 6019, 6030, 6031, 6037, 6045, 6046, 6048, 6049, 6060, 6061, 6064, 6079, 6082, 6091, 6094, 6126, 6162, 6192, 6204, 6205, 6222, 6223, 6228, 6235, 6238, 6243, 6248, 6249, 6253, 6256, 6300, 6336, 6354, 6366, 6367, 6378, 6379, 6382, 6385, 6393, 6394, 6396, 6397, 6409, 6417, 6427, 6430, 6438, 6451, 6474, 6510, 6540, 6552, 6553, 6570, 6571, 6583, 6586, 6601, 6604, 6613, 6622, 6625, 6684, 6714, 6726, 6727, 6730, 6733, 6741, 6742, 6744, 6745, 6757, 6760, 6775, 6778, 6876, 6900, 6918, 6919, 6931, 6934, 6949, 6967, 6968, 6970, 6973, 7050, 7074, 7081, 7089, 7090, 7092, 7093, 7170, 7224, 7236, 7237, 7263, 7264, 7266, 7267, 7297, 7321, 7344, 7398, 7410, 7411, 7429, 7437, 7438, 7440, 7441, 7572, 7584, 7585, 7596, 7597, 7603, 7614, 7615, 7645, 7746, 7758, 7759, 7770, 7771, 7777, 7785, 7786, 7788, 7789, 7822, 7836, 7920, 7932, 7933, 7944, 7945, 7951, 7959, 7962, 7963, 7993, 8094, 8106, 8107, 8118, 8119, 8122, 8125, 8133, 8134, 8136, 8137, 8149, 8170, 8280, 8292, 8293, 8307, 8310, 8311, 8322, 8323, 8341, 8344, 8454, 8466, 8467, 8470, 8473, 8481, 8482, 8484, 8485, 8497, 8505, 8508, 8518, 8610, 8616, 8640, 8641, 8647, 8655, 8656, 8658, 8659, 8673, 8689, 8784, 8790, 8814, 8815, 8821, 8829, 8830, 8832, 8833, 8976, 9003, 9004, 9006, 9007, 9037, 9048, 9138, 9150, 9169, 9177, 9178, 9180, 9181, 9258, 9294, 9295, 9306, 9312, 9324, 9336, 9337, 9343, 9354, 9355, 9385, 9432, 9468, 9499, 9510, 9517, 9526, 9529, 9562, 9606, 9642, 9643, 9672, 9673, 9679, 9684, 9691, 9700, 9703, 9708, 9717, 9718, 9720, 9723, 9733, 9780, 9792, 9816, 9817, 9846, 9847, 9853, 9858, 9859, 9862, 9864, 9865, 9876, 9877, 9882, 9897, 9907, 9910, 9954, 9966, 9978, 9990, 9991, 10020, 10021, 10027, 10032, 10033, 10036, 10039, 10047, 10048, 10050, 10051, 10063, 10081, 10084, 10128, 10140, 10147, 10152, 10161, 10164, 10165, 10194, 10195, 10201, 10206, 10207, 10210, 10213, 10221, 10222, 10224, 10225, 10230, 10232, 10233, 10234, 10237, 10255, 10258, 10302, 10314, 10326, 10335, 10338, 10339, 10356, 10375, 10380, 10381, 10384, 10386, 10387, 10395, 10396, 10398, 10399, 10404, 10411, 10429, 10464, 10476, 10488, 10500, 10509, 10512, 10513, 10530, 10554, 10555, 10558, 10560, 10561, 10569, 10570, 10573, 10578, 10579, 10581, 10582, 10585, 10588, 10591, 10594, 10595, 10603, 10606, 10638, 10647, 10650, 10662, 10674, 10683, 10686, 10687, 10704, 10716, 10717, 10728, 10735, 10743, 10744, 10746, 10747, 10752, 10753, 10756, 10759, 10762, 10765, 10768, 10771, 10773, 10774, 10775, 10777, 10784, 10785, 10800, 10801, 10803, 10804, 10805, 10806, 10807, 10809, 10812, 10816, 10821, 10824, 10827, 10830, 10833, 10836, 10839, 10842, 10845, 10848, 10851, 10857, 10860, 10861, 10863, 10866, 10878, 10890, 10891, 10902, 10909, 10917, 10918, 10920, 10921, 10924, 10926, 10928, 10929, 10932, 10933, 10936, 10947, 10949, 10951, 10986, 10995, 10998, 11010, 11017, 11022, 11034, 11035, 11046, 11052, 11064, 11065, 11076, 11077, 11080, 11083, 11094, 11095, 11106, 11107, 11109, 11110, 11115, 11124, 11125, 11127, 11128]

da = {
  0: 'A',
  1: 'ABU-ABU',
  2: 'ADA',
  3: 'ADIK',
  4: 'ADIL',
  5: 'AGUSTUS',
  6: 'AIR',
  7: 'AJAR',
  8: 'AMAN',
  9: 'AMBIL',
  10: 'APA',
  11: 'APRIL',
  12: 'ASAL',
  13: 'ATAS',
  14: 'AYO',
  15: 'B',
  16: 'BACA',
  17: 'BAGUS',
  18: 'BAHAGIA',
  19: 'BAHAYA',
  20: 'BANGUN',
  21: 'BANYAK',
  22: 'BAU',
  23: 'BAWAH',
  24: 'BERAPA',
  25: 'BERI',
  26: 'BESAR',
  27: 'BESOK',
  28: 'BIASA',
  29: 'BIBI',
  30: 'BICARA',
  31: 'BINGUNG',
  32: 'BIRU',
  33: 'BODOH',
  34: 'BUANG',
  35: 'BUKU',
  36: 'BURUK',
  37: 'C',
  38: 'CALON',
  39: 'CANGGUNG',
  40: 'CANTIK',
  41: 'CARI',
  42: 'CEMAS',
  43: 'CEPAT',
  44: 'CERAH',
  45: 'CERITA',
  46: 'CINTA',
  47: 'COKELAT',
  48: 'CUKUP',
  49: 'CUMA',
  50: 'D',
  51: 'DAHULU',
  52: 'DAMAI',
  53: 'DAN',
  54: 'DARI',
  55: 'DASAR',
  56: 'DATANG',
  57: 'DENGAN',
  58: 'DESEMBER',
  59: 'DETIK',
  60: 'DEWASA',
  61: 'DIA',
  62: 'DIAM',
  63: 'DINGIN',
  64: 'DOKTER',
  65: 'E',
  66: 'EMAS',
  67: 'F',
  68: 'FAVORIT',
  69: 'FEBRUARI',
  70: 'FILM',
  71: 'G',
  72: 'GANTENG',
  73: 'GELAP',
  74: 'GEREJA',
  75: 'GURU',
  76: 'H',
  77: 'HAI',
  78: 'HARGA',
  79: 'HARI',
  80: 'HIJAU',
  81: 'HITAM',
  82: 'HOBI',
  83: 'HUJAN',
  84: 'I',
  85: 'INI',
  86: 'ISYARAT',
  87: 'ITU',
  88: 'J',
  89: 'JADI',
  90: 'JAHAT',
  91: 'JALAN',
  92: 'JAM',
  93: 'JANGAN',
  94: 'JANUARI',
  95: 'JELAS',
  96: 'JELEK',
  97: 'JULI',
  98: 'JUMPA',
  99: 'JUNI',
  100: 'K',
  101: 'KABAR',
  102: 'KAKAK',
  103: 'KAKEK',
  104: 'KALAU',
  105: 'KAMI',
  106: 'KAPAN',
  107: 'KASIH',
  108: 'KECIL',
  109: 'KEJUT',
  110: 'KELOMPOK',
  111: 'KEMARIN',
  112: 'KEMUDIAN',
  113: 'KERJA',
  114: 'KETUA',
  115: 'KITA',
  116: 'KITAB',
  117: 'KOSONG',
  118: 'KRISTEN',
  119: 'KUNING',
  120: 'KURANG',
  121: 'KURSI',
  122: 'L',
  123: 'LAGI',
  124: 'LAHIR',
  125: 'LAKI-LAKI',
  126: 'LALU',
  127: 'LAMBAT',
  128: 'LAMPU',
  129: 'LANCAR',
  130: 'LARI',
  131: 'LEBIH',
  132: 'LIHAT',
  133: 'LUSA',
  134: 'M',
  135: 'MACET',
  136: 'MAIN',
  137: 'MAKA',
  138: 'MAKAN',
  139: 'MALAM',
  140: 'MALAS',
  141: 'MANA',
  142: 'MANDI',
  143: 'MARAH',
  144: 'MARET',
  145: 'MASA',
  146: 'MAU',
  147: 'MEI',
  148: 'MEJA',
  149: 'MENDUNG',
  150: 'MENGAPA',
  151: 'MENIT',
  152: 'MERAH',
  153: 'MEREKA',
  154: 'MINGGU',
  155: 'MINUM',
  156: 'MUDA',
  157: 'MURID',
  158: 'N',
  159: 'NANTI',
  160: 'NENEK',
  161: 'NOVEMBER',
  162: 'O',
  163: 'OKTOBER',
  164: 'ORANG',
  165: 'ORANGE',
  166: 'P',
  167: 'PAGI',
  168: 'PAMAN',
  169: 'PANAS',
  170: 'PANGGIL',
  171: 'PERAK',
  172: 'PERCAYA',
  173: 'PEREMPUAN',
  174: 'PERGI',
  175: 'PINTAR',
  176: 'POLISI',
  177: 'PULANG',
  178: 'PUNYA',
  179: 'PUTIH',
  180: 'Q',
  181: 'R',
  182: 'RABU',
  183: 'RAJIN',
  184: 'REMAJA',
  185: 'RUMAH',
  186: 'S',
  187: 'SABTU',
  188: 'SAHABAT',
  189: 'SAMA',
  190: 'SAMPAI',
  191: 'SEDIH',
  192: 'SEDIKIT',
  193: 'SEHAT',
  194: 'SEKARANG',
  195: 'SEKOLAH',
  196: 'SELAMAT',
  197: 'SELASA',
  198: 'SEMANGAT',
  199: 'SEMUA',
  200: 'SENIN',
  201: 'SORE',
  202: 'SUCI',
  203: 'SUKA',
  204: 'T',
  205: 'TAHU',
  206: 'TAHUN',
  207: 'TAKUT',
  208: 'TENGAH',
  209: 'TENTANG',
  210: 'TENTARA',
  211: 'TEPAT',
  212: 'TERIMA',
  213: 'TIDAK',
  214: 'TIDUR',
  215: 'TINGGAL',
  216: 'TUA',
  217: 'TUHAN',
  218: 'TULIS',
  219: 'U',
  220: 'ULANG',
  221: 'UMUR',
  222: 'UNGU',
  223: 'V',
  224: 'W',
  225: 'WAKIL',
  226: 'WAKTU',
  227: 'WANGI',
  228: 'X',
  229: 'Y',
  230: 'YAITU',
  231: 'Z'
}

def processData(pose, right, left, face):
  global arr_frame
  lms_p, lms_r, lms_l, lms_f = np.array(pose), np.array(right), np.array(left), np.array(face)
  # normalize xyz landmark
  ssx_p, ssy_p, ssz_p = lms_p[:, 0], lms_p[:, 1], lms_p[:, 2]
  ssx_r, ssy_r, ssz_r = lms_r[:, 0], lms_r[:, 1], lms_r[:, 2]
  ssx_l, ssy_l, ssz_l = lms_l[:, 0], lms_l[:, 1], lms_l[:, 2]
  ssx_f, ssy_f, ssz_f = lms_f[:, 0], lms_f[:, 1], lms_f[:, 2]
  # Standardization Z
  p_std, r_std, l_std, f_std = ssz_p.std(), ssz_r.std(), ssz_l.std(), ssz_f.std()
  if p_std != 0:
      lms_p[:, 2] = (ssz_p - ssz_p.mean()) / p_std
  if r_std != 0:
      lms_r[:, 2] = (ssz_r - ssz_r.mean()) / r_std
  if l_std != 0:
      lms_l[:, 2] = (ssz_l - ssz_l.mean()) / l_std
  if f_std != 0:
      lms_f[:, 2] = (ssz_f - ssz_f.mean()) / f_std
  # Min max normalization X and Y
  # Pose normalization
  jx, jy = ssx_p.max() - ssx_p.min(), ssy_p.max() - ssy_p.min()
  if jx == jy == 0:
      pass
  else:
      if jx > jy:
          dif = (jx - jy) / 2
          bbx1, bby1, bbx2, bby2 = ssx_p.min(), ssy_p.min() - dif, ssx_p.min() + jx, ssy_p.min() + jx - dif
      elif jx < jy:
          dif = (jy - jx) / 2
          bbx1, bby1, bbx2, bby2 = ssx_p.min() - dif, ssy_p.min(), ssx_p.min() + jy - dif, ssy_p.min() + jy
      lms_p[:, 0], lms_p[:, 1] = (ssx_p - bbx1) / (bbx2 - bbx1), (ssy_p - bby1) / (bby2 - bby1)
  # Right hand normalization
  jx, jy = ssx_r.max() - ssx_r.min(), ssy_r.max() - ssy_r.min()
  if jx == jy == 0:
      pass
  else:
      if jx > jy:
          dif = (jx - jy) / 2
          bbx1, bby1, bbx2, bby2 = ssx_r.min(), ssy_r.min() - dif, ssx_r.min() + jx, ssy_r.min() + jx - dif
      elif jx < jy:
          dif = (jy - jx) / 2
          bbx1, bby1, bbx2, bby2 = ssx_r.min() - dif, ssy_r.min(), ssx_r.min() + jy - dif, ssy_r.min() + jy
      lms_r[:, 0], lms_r[:, 1] = (ssx_r - bbx1) / (bbx2 - bbx1), (ssy_r - bby1) / (bby2 - bby1)
  # Left hand normalization
  jx, jy = ssx_l.max() - ssx_l.min(), ssy_l.max() - ssy_l.min()
  if jx == jy == 0:
      pass
  else:
      if jx > jy:
          dif = (jx - jy) / 2
          bbx1, bby1, bbx2, bby2 = ssx_l.min(), ssy_l.min() - dif, ssx_l.min() + jx, ssy_l.min() + jx - dif
      elif jx < jy:
          dif = (jy - jx) / 2
          bbx1, bby1, bbx2, bby2 = ssx_l.min() - dif, ssy_l.min(), ssx_l.min() + jy - dif, ssy_l.min() + jy
      lms_l[:, 0], lms_l[:, 1] = (ssx_l - bbx1) / (bbx2 - bbx1), (ssy_l - bby1) / (bby2 - bby1)
  # Face normalization
  jx, jy = ssx_f.max() - ssx_f.min(), ssy_f.max() - ssy_f.min()
  if jx == jy == 0:
      pass
  else:
      if jx > jy:
          dif = (jx - jy) / 2
          bbx1, bby1, bbx2, bby2 = ssx_f.min(), ssy_f.min() - dif, ssx_f.min() + jx, ssy_f.min() + jx - dif
      elif jx < jy:
          dif = (jy - jx) / 2
          bbx1, bby1, bbx2, bby2 = ssx_f.min() - dif, ssy_f.min(), ssx_f.min() + jy - dif, ssy_f.min() + jy
      lms_f[:, 0], lms_f[:, 1] = (ssx_f - bbx1) / (bbx2 - bbx1), (ssy_f - bby1) / (bby2 - bby1)
  # Collect all landmark
  lms_p, lms_r, lms_l, lms_f = list(lms_p), list(lms_r), list(lms_l), list(lms_f)
  lms_all = lms_p + lms_r + lms_l + lms_f
  arr_frame.append(lms_all)
  return lms_all

def predict():
  global arr_frame
  arr_fs = np.array(arr_frame)
  arr_t = []
  for i in range(64):
    arr_t.append(arr_fs[int(i * arr_fs.shape[0] / 64)])
  arr_fs = np.array(arr_t)
  arr_fs = arr_fs.ravel()
  arr_fs = np.delete(arr_fs, rd, axis=0)
  X_train = arr_fs.reshape(-1, 58*64*3-len(rd), 1)
  arr_frame = []
  return X_train

  y_pred = np.argmax(y_pred_probabilities, axis=1)
  return da[y_pred[0]]

def getValue(x):
  return da[y_pred[0]]