import numpy as np
import matplotlib.pyplot as plt

def f(x):
    return np.log2(np.cos(x))

def get_chebyshev_nodes(a, b, n):
    k = np.arange(0, n)
    nodes = 0.5 * (a + b) + 0.5 * (b - a) * np.cos((2*k + 1) * np.pi / (2*n))
    return nodes

def get_uniform_nodes(start, stop, num):
    step = (stop - start) / (num - 1)
    return [start + i * step for i in range(num)]

def compute_coefficients(nodes, values):
    n = len(nodes)
    coefficients = np.copy(values)

    for i in range(1, n):
        for j in range(n-1, i-1, -1):
            coefficients[j] = (coefficients[j] - coefficients[j-1]) / (nodes[j] - nodes[j-i])

    return coefficients

def newton_interpolation(x, nodes, coefficients):
    n = len(nodes)
    result = coefficients[-1]

    for i in range(n-2, -1, -1):
        result = result * (x - nodes[i]) + coefficients[i]

    return result

a, b = -np.pi / 3, np.pi / 3
degree = int(input("Enter the degree of the polynomial: "))

uniform_nodes = get_uniform_nodes(a, b, degree+1)
uniform_values = f(uniform_nodes)
uniform_coefficients = compute_coefficients(uniform_nodes, uniform_values)

chebyshev_nodes_values = get_chebyshev_nodes(a, b, degree+1)
chebyshev_values = f(chebyshev_nodes_values)
chebyshev_coefficients = compute_coefficients(chebyshev_nodes_values, chebyshev_values)

print("Uniform distributed nodes:")
print(", ".join([f"({xi:.4f}, {fi:.4f})" for xi, fi in zip(uniform_nodes, uniform_values)]))

print("\nChebyshev nodes:")
print(", ".join([f"({xi:.4f}, {fi:.4f})" for xi, fi in zip(chebyshev_nodes_values, chebyshev_values)]))

error_point = float(input("\nEnter the point to find the error: "))

uniform_error = abs(f(error_point) - newton_interpolation(error_point, uniform_nodes, uniform_coefficients))
chebyshev_error = abs(f(error_point) - newton_interpolation(error_point, chebyshev_nodes_values, chebyshev_coefficients))

print(f"\nError uniform distributed: {error_point}: {uniform_error:.8f}")
print(f"Error Chebyshev: {error_point}: {chebyshev_error:.8f}")

x_values = np.linspace(a, b, 1000)
uniform_interpolated_values = [newton_interpolation(x, uniform_nodes, uniform_coefficients) for x in x_values]
chebyshev_interpolated_values = [newton_interpolation(x, chebyshev_nodes_values, chebyshev_coefficients) for x in x_values]

plt.plot(x_values, f(x_values), label='True Function')
plt.plot(uniform_nodes, uniform_values, 'o', label='Uniform Nodes')
plt.plot(x_values, uniform_interpolated_values, label='Uniform Interpolation')
plt.plot(chebyshev_nodes_values, chebyshev_values, 'o', label='Chebyshev Nodes')
plt.plot(x_values, chebyshev_interpolated_values, label='Chebyshev Interpolation')
plt.legend()
plt.xlabel('x')
plt.ylabel('y')
plt.title(f'Newton Interpolation for log2(cos(x)) (Degree={degree})')
plt.show()
