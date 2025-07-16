library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.NUMERIC_STD.ALL;

entity Counter is
    Port ( clk : in std_logic;
           up : in std_logic;   
           down : in std_logic;       
           reset : in std_logic;   
           adauga_cifra : in std_logic;    
           data_out: out std_logic_vector(3 downto 0));
end Counter;

architecture Behavioral of Counter is

signal counter : std_logic_vector(3 downto 0) := "0000";

begin

process(clk, up, down, reset, adauga_cifra)
begin
    if reset = '1' then
        counter <= "0000";
    elsif rising_edge(clk) then
        if up = '1' then
            counter <= counter + 1;
        elsif down = '1' then
            counter <= counter - 1;
        end if;
    end if;
data_out <= counter;
end process;

end Behavioral;
