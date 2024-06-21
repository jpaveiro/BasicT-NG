export function capitalize(text: string) {
  const splitedName = text.toLowerCase().split(' ');
  const capitalizedName = splitedName.map((name) => {
    return name.charAt(0).toUpperCase() + name.slice(1);
  });
  return capitalizedName.join(' ');
}